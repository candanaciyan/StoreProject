package com.tobeto.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.repository.UserRepository;

import io.jsonwebtoken.Claims;
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
//
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
//	private String tokenUser(Kullanici kullanici) {
//		JwtBuilder builder = Jwts.builder();
//
//		List<Rol> rollerKullanici = kullanici.getRoller();
//
//		String[] roller = new String[rollerKullanici.size()];
//		for (int i = 0; i < rollerKullanici.size(); i++) {
//			roller[i] = rollerKullanici.get(i).getAdi();
//		}

	// add custom keys
//	Map<String, Object> customKeys = new HashMap<>();customKeys.put("roller",roller);builder=builder.claims(customKeys);
//
//	Instant tarih = Instant.now().plus(15, ChronoUnit.MINUTES);
//
//	builder=builder.subject("login").id(kullanici.getEmail()).issuedAt(new Date()).expiration(Date.from(tarih));
//
//	return builder.signWith(getKey()).compact();
//	}

	public Claims tokenControl(String token) {
		JwtParser builder = Jwts.parser().verifyWith(getKey()).build();
		return builder.parseSignedClaims(token).getPayload();
	}

	private SecretKey getKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
		return key;
	}

//	public String createToken(User user) {
//		JwtBuilder builder = Jwts.builder();
//
//		List<Roller> rollerKullanici = kullanicilar.getRollers();
//
//		String[] roller = new String[rollerKullanici.size()];
//		for (int i = 0; i < rollerKullanici.size(); i++) {
//			roller[i] = rollerKullanici.get(i).getRol();
//		}
//
//		// add custom keys
//		Map<String, Object> customKeys = new HashMap<>();
//		customKeys.put("roller", roller);
//		customKeys.put("kullanicilarId", kullanicilar.getId().toString());
//		builder = builder.claims(customKeys);
//
//		Instant tarih = Instant.now().plus(15, ChronoUnit.MINUTES);
//
//		builder = builder.subject("login").id(kullanicilar.getKullaniciAdi()).issuedAt(new Date())
//				.expiration(Date.from(tarih));
//
//		return builder.signWith(getKey()).compact();
//	}
}
