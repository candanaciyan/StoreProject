package com.tobeto.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Role;
import com.tobeto.entity.User;
import com.tobeto.repository.UserRepository;

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
	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//token uretmek icin gereken 3 dependency var onu pom xml e ekliyoruz jjwtli olanlar onlar

	public String createToken(User user) {
		JwtBuilder builder = Jwts.builder();
//kullanicilar degiskeni uzerinden get rollers diyoruz bu bize list roller veriyor

		// bu iliskiden dolayi mtm olmadigi icin role u list degil normal aldim
		// sonra her rol icin for dongusuyle string arrayin icerisine rollerin
		// isimlerini yaziyorum
		Role userRole = user.getRole();
//		String[] roles = new String[userRole.size()];
//		for (int i = 0; i < userRole.size(); i++) {
//			roles[i] = userRole.get(i).getRole();
//		}

		// add custom keys
		Map<String, Object> customKeys = new HashMap<>();
		customKeys.put("role", userRole);// token icine konan role bilgisi
		customKeys.put("userId", user.getId().toString());// token icine konan id bilgisi
		customKeys.put("email", user.getEmail());
		builder = builder.claims(customKeys);

		Instant time = Instant.now().plus(15, ChronoUnit.MINUTES);

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

//	@Transactional
//	public String tokenUret(UserDetails user) {
//		Optional<Kullanici> oKullanici = kullaniciRepository.findByEmail(user.getUsername());
//		if (oKullanici.isPresent()
//				&& passwordEncoder.matches(user.getPassword(), oKullanici.get().getSifre())) {
//			// username'e göre veritabanında kayıt bulundu ve şifresi de onaylandı
//			return tokenUser(oKullanici.get());
//
//		}
//		throw new UsernameNotFoundException("Not Found");
//	}
//

}
