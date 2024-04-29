package com.tobeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.service.LoginService;
import com.tobeto.service.TokenService;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenService tokenService;

//	@PostMapping("/login")
//	public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
//		String token = loginService.login(dto.getEmail(), dto.getPassword());
//		LoginResponseDTO responseDTO = new LoginResponseDTO();
//		responseDTO.setToken(token);
//		return responseDTO;
//	}
	// Buradaki temel işlev, kullanıcı giriş bilgilerini almak, kimlik doğrulamasını
	// yapmak, bir token oluşturmak ve bu token'ı kullanıcıya geri döndürmektir. Bu
	// tip işlevler, genellikle kullanıcıların uygulamaya oturum açmasını sağlamak
	// için kullanılır.

//	@PostMapping("/login")
//	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
//		Optional<Kullanicilar> oKullanicilar = kullaniciService.getKullanici(dto.getEmail());
//		if (oKullanicilar.isPresent()
//				&& passwordEncoder.matches(dto.getPassword(), oKullanicilar.get().getSifre())) {
//			String token = tokenService.createToken(oKullanicilar.get());
//			return ResponseEntity.ok(new LoginResponseDTO(token));
//		} else {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//		}
//	}

}
