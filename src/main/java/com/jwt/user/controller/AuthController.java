package com.jwt.user.controller;

import com.jwt.user.dto.ReqRes;
import com.jwt.user.dto.SigninRequest;
import com.jwt.user.dto.SignupRequest;
import com.jwt.user.entity.Role;
import com.jwt.user.repository.RoleRepository;
import com.jwt.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private RoleRepository roleRepository;

	@PostMapping("/signup")
	public ResponseEntity<ReqRes> signUp(@RequestBody SignupRequest signUpRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUpRequest));
	}

	@PostMapping("/signin")
	public ResponseEntity<ReqRes> signIn(@RequestBody SigninRequest signInRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(signInRequest));
	}

	@PostMapping("/add-roles")
	public ResponseEntity<Role> addRoles(@RequestBody Role role) {
		return ResponseEntity.status(HttpStatus.CREATED).body(roleRepository.save(role));
	}

}
