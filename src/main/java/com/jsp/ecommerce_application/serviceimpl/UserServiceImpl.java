package com.jsp.ecommerce_application.serviceimpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.jsp.ecommerce_application.entity.AccessToken;
import com.jsp.ecommerce_application.entity.Customer;
import com.jsp.ecommerce_application.entity.RefreshToken;
import com.jsp.ecommerce_application.entity.Seller;
import com.jsp.ecommerce_application.entity.User;
import com.jsp.ecommerce_application.enums.UserRole;
import com.jsp.ecommerce_application.exception.EmailNotFoundException;
import com.jsp.ecommerce_application.exception.OtpExpiredException;
import com.jsp.ecommerce_application.mail.MailService;
import com.jsp.ecommerce_application.mapper.UserMapper;
import com.jsp.ecommerce_application.repo.AccessTokenRepository;
import com.jsp.ecommerce_application.repo.RefreshTokenRepository;
import com.jsp.ecommerce_application.repo.UserRepository;
import com.jsp.ecommerce_application.requestdto.AuthRequest;
import com.jsp.ecommerce_application.requestdto.OtpVerificationRequest;
import com.jsp.ecommerce_application.requestdto.UserRequest;
import com.jsp.ecommerce_application.responsedto.AuthResponse;
import com.jsp.ecommerce_application.responsedto.UserResponse;
import com.jsp.ecommerce_application.security.JWTService;
import com.jsp.ecommerce_application.service.UserService;
import com.jsp.ecommerce_application.utility.MessageData;
import com.jsp.ecommerce_application.utility.ResponseStructure;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {

	private  final UserRepository userRepository;

	private  final UserMapper userMapper;

	private final Cache<String, User> userCache;

	private final  Cache<String, String> otpCache;

	private final Random random;

	private final MailService mailService;

	private final AuthenticationManager authenticationManager;

	private final JWTService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final AccessTokenRepository accessTokenRepository;

	private RefreshTokenRepository refreshTokenRepository;

	/**Whenever we use @Value we should remove @AllArgsConstructor and generate our own constructors with fields
	 * which are not annotated over @Value for Constructor Injection*/


	@Value("${application.jwt.secret}")
	private String secret2;

	@Value("${application.jwt.access_expiry_seconds}")
	private long accessExpirySeconds;

	@Value("${application.jwt.refresh_expiry_seconds}")
	private long refreshExpirySeconds;

	@Value("${application.cookie.domain}")
	private String domain;

	@Value("${application.cookie.same_site}")
	private String sameSite;

	@Value("${application.cookie.secure}")
	private boolean secure;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, Cache<String, User> userCache,
			Cache<String, String> otpCache, Random random, MailService mailService,
			AuthenticationManager authenticationManager, JWTService jwtService, PasswordEncoder passwordEncoder,
			AccessTokenRepository accessTokenRepository, RefreshTokenRepository refreshTokenRepository) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.userCache = userCache;
		this.otpCache = otpCache;
		this.random = random;
		this.mailService = mailService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.accessTokenRepository = accessTokenRepository;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest, UserRole userRole) {
		User user = null;
		MessageData messageData = new MessageData();

		System.out.println(userRequest.getEmail());
		System.out.println(userRequest.getPassword());

		switch (userRole) {
		case CUSTOMER -> user = new Customer();
		case SELLER -> user = new Seller();
		}
		if (user != null) {
			user = userMapper.mapToUser(userRequest, user);
			String[] usernameArr = user.getEmail().split("@");

			user.setUserName(usernameArr[0]);
			user.setEmailVerified(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			int number = random.nextInt(100000, 999999);
			String otpValue = String.valueOf(number);

			user.setUserRole(userRole);

			System.out.println(user.getEmail());
			System.out.println(user.getPassword());

			userCache.put(user.getEmail(), user);
			otpCache.put(user.getEmail(), otpValue);

			messageData.setTo(user.getEmail());
			messageData.setSubject("verify your email using otp");
			messageData.setSentDate(new Date());
			messageData.setText("your otp : " + otpValue);
			try {
				mailService.sendMail(messageData);

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new ResponseStructure<UserResponse>().setStatus(HttpStatus.ACCEPTED.value())
						.setMessage("Seller Created").setData(userMapper.mapToUserResponse(user)));

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpVerificationRequest otpVerificationRequest) {
		User user = userCache.getIfPresent(otpVerificationRequest.getEmail());
		String otp = otpCache.getIfPresent(otpVerificationRequest.getEmail());
		System.out.println(otp);
		if (otp == null)
			throw new OtpExpiredException("otp expired");

		if (otpVerificationRequest.getOtp().equals(otp)) {
			user = userRepository.save(user);

			MessageData messageData = new MessageData();
			messageData.setTo(user.getEmail());
			messageData.setSubject("User registration is done");
			messageData.setSentDate(new Date());
			messageData.setText("your username" + user.getUserName());

			try {
				mailService.sendMail(messageData);

			} catch (MessagingException e) {
				throw new EmailNotFoundException("Failed to send confirmation mail");
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ResponseStructure<UserResponse>().setStatus(HttpStatus.OK.value())
						.setMessage("User registration successful").setData(userMapper.mapToUserResponse(user)));
	}

	//********************login*******************************************

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> login(AuthRequest authRequest) {
		System.out.println("in login");
		Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));

		if(authentication.isAuthenticated()) {


		return userRepository.findByUserName(authRequest.getUserName()).map(user -> {
				HttpHeaders headers=new HttpHeaders();
				grantAccessToken(headers, user);
				grantRefreshtoken(headers, user);
				
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new ResponseStructure<AuthResponse>()
						.setStatus(HttpStatus.OK.value())
						.setMessage("login successfully")
						.setData(AuthResponse.builder()
								.userId(user.getUserId())
								.userName(user.getUserName())
								.accessExpiration(accessExpirySeconds)
								.refreshExpiration(refreshExpirySeconds)
								.build()));						
			}).orElseThrow(()-> new UsernameNotFoundException(" User name is not found"));

		}
		else 
			throw new BadCredentialsException("Bad Request");
	}



	//*************************grandAccessToken*************************

	private  void grantAccessToken(HttpHeaders httpHeaders, User user){

		String accessToken =	jwtService.createJWTTocken(user.getUserName(), 3600000, user.getUserRole().toString());

		AccessToken access = new AccessToken();
		access.setToken(accessToken);
		access.setExpiration(LocalDateTime.now().plusSeconds(3600));
		access.setUser(user);

		httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("at", accessToken, accessExpirySeconds));


		accessTokenRepository.save(access);

	}

	private void grantRefreshtoken(HttpHeaders httpHeaders, User user){

		String refreshToken =	jwtService.createJWTTocken(user.getUserName(), 1000*60*60*24*15, user.getUserRole().toString());

		RefreshToken access = new RefreshToken();
		access.setToken(refreshToken);
		access.setExpiration(LocalDateTime.now().plusSeconds(60*60*24*15));
		access.setUser(user);

		httpHeaders.add(HttpHeaders.SET_COOKIE, generateCookie("rt", refreshToken, refreshExpirySeconds));


		refreshTokenRepository.save(access);

	}


	private String generateCookie(String cokkieName, String value , Long maxAge) {

		return 	ResponseCookie.from(cokkieName, value )
				.domain(domain)
				.path("/")
				.maxAge(maxAge)
				.sameSite(sameSite)
				.httpOnly(true)
				.secure(secure)
				.build().
				toString();
	}
	

	

}
