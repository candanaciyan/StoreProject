package com.tobeto.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.SuccessResponseDTO;
import com.tobeto.dto.user.AllUsersResponseDTO;
import com.tobeto.dto.user.CreateUserRequestDTO;
import com.tobeto.entity.User;
import com.tobeto.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;
	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

//	@PostMapping("/create")
//	public CreateUserResponseDTO createUser(@RequestBody CreateUserRequestDTO dto) {
//		User user = requestMapper.map(dto, User.class);
//		user = userService.addUser(user);
//		return responseMapper.map(user, CreateUserResponseDTO.class);
//	}

	@PostMapping("/user/create")
	public ResponseEntity<SuccessResponseDTO> createUser(@RequestBody CreateUserRequestDTO dto) {
		User user = requestMapper.map(dto, User.class);
		userService.createUser(user);
		return ResponseEntity.ok(new SuccessResponseDTO("kisi oluşturuldu"));
	}
//	}bunu kayit formunda kullanabilirsin

//	@GetMapping
//	public List<PersonelSorgulaResponseDTO> personelSorgular() {
//		return personelService.getPersoneller().stream()
//				.map(p -> responseMapper.map(p, PersonelSorgulaResponseDTO.class)).toList();
//	}

	@GetMapping("/allusers")
	public ResponseEntity<List<AllUsersResponseDTO>> getAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		List<AllUsersResponseDTO> sonuc = new ArrayList<>();
		allUsers.forEach(user -> {
			sonuc.add(responseMapper.map(user, AllUsersResponseDTO.class));
		});

		return ResponseEntity.ok(sonuc);
	}
	// bunu tum kullanicilari isterken kullanabilirsin

//	@PostMapping("/sifreDegistir")
//	public ResponseEntity<SuccessResponseDTO> sifreDegistir(@RequestBody AccountRequestDTO dto,
//			Principal principal) {
//		boolean sonuc = kullaniciService.sifreDegistir(dto.getEskiSifre(), dto.getYeniSifre(),
//				principal.getName());
//		if (sonuc) {
//			return ResponseEntity.ok(new SuccessResponseDTO("şifre değiştirildi"));
//		} else {
//			return ResponseEntity.internalServerError().build();
//		}
//	}sifre degistirmek icin

}
