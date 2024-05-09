package com.tobeto.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tobeto.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	@Value("${application.security.jwt.SECRET_KEY}")
	private String KEY;

	public String createToken(User user) {
		JwtBuilder builder = Jwts.builder();
		Map<String, Object> customKeys = new HashMap<>();
		customKeys.put("role", user.getRole().getName());
		customKeys.put("email", user.getEmail());
		customKeys.put("name", user.getName());
		customKeys.put("surname", user.getSurname());
		builder = builder.claims(customKeys);

		Instant time = Instant.now().plus(5, ChronoUnit.MINUTES);

		builder = builder.subject("login").id(user.getEmail()).issuedAt(new Date())
				.expiration(Date.from(time));

		return builder.signWith(getKey()).compact();
	}

	public Claims tokenControl(String token) {
		JwtParser builder = Jwts.parser().verifyWith(getKey()).build();
		return builder.parseSignedClaims(token).getPayload();
	}

	private SecretKey getKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
		return key;
	}

}
