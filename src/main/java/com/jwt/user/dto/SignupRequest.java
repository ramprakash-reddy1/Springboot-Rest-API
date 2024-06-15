package com.jwt.user.dto;

import java.util.Set;

import lombok.Data;

@Data
public class SignupRequest {

	private String email;
	private String password;
	private String confirmPassword;
	private Set<String> roles;

}
