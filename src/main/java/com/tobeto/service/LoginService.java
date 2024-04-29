package com.tobeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserService userService;

//	public String login(String email, String password) {
//		Optional<User> oUser = userService.getUserByEmail(email);
//		if (oUser.isPresent() && oUser.get().getPassword().equals(password)) {
//			String token = tokenService.createToken(oUser.get());
//			return token;
//		} else {
//			throw new RuntimeException("Login Error");
//		}
//	}
}
