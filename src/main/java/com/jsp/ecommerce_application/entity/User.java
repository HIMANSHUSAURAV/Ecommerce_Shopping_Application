package com.jsp.ecommerce_application.entity;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.jsp.ecommerce_application.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(generator = "custom")
	private String userId;
	private String username;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private List<UserRole> roles;
	private boolean isEmailVerified;
	private boolean isDeleted;


}
