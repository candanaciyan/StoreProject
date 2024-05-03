package com.tobeto.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.login.LoginRequestDTO;
import com.tobeto.dto.login.LoginResponseDTO;
import com.tobeto.entity.User;
import com.tobeto.service.LoginService;
import com.tobeto.service.TokenService;
import com.tobeto.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private TokenService tokenService;

	// email ve passworde bakmayacagiz onlari veri tabanindan isteyecegiz
	// eger hersey dogruysa ondan sonra token uretip geri dondurucez onu
	// yanlissa hata geri dondurucez

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
		Optional<User> oUser = userService.getUser(dto.getEmail());
		if (oUser.isPresent()
				&& passwordEncoder.matches(dto.getPassword(), oUser.get().getPassword())) {
			String token = tokenService.createToken(oUser.get());
			return ResponseEntity.ok(new LoginResponseDTO(token));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

//	@PostMapping("/login")
//	public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
//		String token = loginService.login(dto.getEmail(), dto.getPassword());
//		LoginResponseDTO responseDTO = new LoginResponseDTO();
//		responseDTO.setToken(token);
//		return responseDTO;
//	}
	// armuttan gelen bu
	// Buradaki temel işlev, kullanıcı giriş bilgilerini almak, kimlik doğrulamasını
	// yapmak, bir token oluşturmak ve bu token'ı kullanıcıya geri döndürmektir. Bu
	// tip işlevler, genellikle kullanıcıların uygulamaya oturum açmasını sağlamak
	// için kullanılır.v
//	loginresponsedto ve requestdto sadece bu metoda ozel oldugu icin bunlari service classlarina aktarmiyoruz
//	cunku service classinin login metodu baska yerlerden de cagiriliyor olabilir

}
