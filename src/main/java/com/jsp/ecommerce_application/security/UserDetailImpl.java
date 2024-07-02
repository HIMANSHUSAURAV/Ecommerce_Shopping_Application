package com.jsp.ecommerce_application.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jsp.ecommerce_application.entity.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailImpl implements UserDetails {
	

    private final String userName;
    private final String password;
    private final List<GrantedAuthority> grantedAuthorities;

    public UserDetailImpl(User user) {
        userName = user.getUserName();
        password = user.getPassword();
        grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getUserRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

	
}
