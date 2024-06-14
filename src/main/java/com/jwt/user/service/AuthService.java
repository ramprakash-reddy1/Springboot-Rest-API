package com.jwt.user.service;

import com.jwt.user.config.JWTUtils;
import com.jwt.user.dto.ReqRes;

import com.jwt.user.entity.Role;
import com.jwt.user.entity.User;
import com.jwt.user.repository.RoleRepository;
import com.jwt.user.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JWTUtils jwtUtils;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RoleRepository roleRepository;

	public ReqRes signUp(ReqRes registrationRequest) {
		ReqRes response = new ReqRes();
		try {

			Optional<User> existingUser = userRepo.findByEmail(registrationRequest.getEmail());
			if (existingUser.isPresent()) {
				throw new Exception("User with this email already exists");
			}
			if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
				throw new Exception("Passwords do not match");
			}
			User user = new User();

			Set<Role> userRoles = new HashSet<>();

			for (String roleName : registrationRequest.getRoles()) {
				Optional<Role> roleOptional = roleRepository.findByName(roleName);
				Role role = roleOptional.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
				userRoles.add(role);
			}
			user.setEmail(registrationRequest.getEmail());
			user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			user.setRoles(userRoles);
			User ourUserResult = userRepo.save(user);
			if (ourUserResult != null && ourUserResult.getId() > 0) {
				response.setUsers(ourUserResult);
				response.setMessage("User Saved Successfully");
				response.setStatusCode(200);
			}
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setError(e.getMessage());
		}
		return response;
	}

	public ReqRes signIn(ReqRes signinRequest) {
		ReqRes response = new ReqRes();

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
			User user = userRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
			System.out.println("USER IS: " + user.getUsername().toString());
			String jwt = jwtUtils.generateToken(user);
			response.setStatusCode(200);
			response.setToken(jwt);
			response.setExpirationTime("24Hr");
			response.setMessage("Successfully Signed In");
		} catch (Exception e) {
			response.setStatusCode(500);
			response.setMessage("Invalid credentails");
			response.setError(e + "");
		}
		return response;
	}

	public ReqRes refreshToken(ReqRes refreshTokenReqiest) {
		ReqRes response = new ReqRes();
		String email = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
		User user = userRepo.findByEmail(email).orElseThrow();
		if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), user)) {
			var jwt = jwtUtils.generateToken(user);
			response.setStatusCode(200);
			response.setToken(jwt);
			response.setRefreshToken(refreshTokenReqiest.getToken());
			response.setExpirationTime("24Hrs");
			response.setMessage("Successfully Refreshed Token");
		}
		response.setStatusCode(500);
		return response;
	}
}
