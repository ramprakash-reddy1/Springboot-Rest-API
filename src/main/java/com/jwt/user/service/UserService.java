package com.jwt.user.service;



import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.user.entity.User;
import com.jwt.user.repository.UserRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	return userRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));


	}
	 private Set<SimpleGrantedAuthority> getAuthority(User user) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	        user.getRoles().forEach(role -> {
	            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
	        });
	        return authorities;
	    }
}
