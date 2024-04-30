package com.tobeto.controller;

import java.security.Principal;
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
import com.tobeto.dto.user.PasswordChangeRequestDTO;
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
	// responseenttity kullanicaz bunu aliskanlik edin geriye
	// cookie response status hata gibi bircok sey dondurebiliyoruz cunku
//	sonuc degiskeni vererek olusturduk
//	sonuc un icine ekleme yapacagiz ama tek tek yapacagiz
//	cunku bizim modelmapperimiz her seferinde bir tane objeyi cevirebiliyor
//	listeyi komple ceviremiyor bu yuzden
//	for each yazarak her bir yazilim ilan tipindeki obje icin ilani alacagim 
//	her bir ilan icin modelmapperdan gecirip bunu sonuc listesine ekleyecegim
//	ilan verip donusecegi TumYazilimIlanlarResponseDTO.class verdik 
//	olusturdugu objeyi de sonuc.add icine aticaz
//	map ifadesi  modelmapper classi icindeki bir classtan diger classa cevirme islemini yapan metodumuzun adi map.
//	bu isleme mapping demisler

	@PostMapping("/changepassword")
	public ResponseEntity<SuccessResponseDTO> changePassword(
			@RequestBody PasswordChangeRequestDTO dto, Principal principal) {
		boolean result = userService.changePassword(dto.getOldPassword(), dto.getNewPassword(),
				principal.getName());
		// burdan donen degeri sonuca atip
//		if sonuc true ise geriye bunu dondursun 
//		else ise hata donsun		
		if (result) {
			return ResponseEntity.ok(new SuccessResponseDTO("şifre değiştirildi"));
		} else {
			return ResponseEntity.internalServerError().build();
		}
//	}sifre degistirmek icin
//		eski sifre ve yeni sifre istiyorum kullanici adi zaten token icerisinde dolayisiyla ben token icerisinden gelen email bilgisini alarak bu kimmis diye bu bilgiyi kullanabilirim
//		bundan parametre olarak email almiyorum onu tokendan alacagim
//		buraya bir tane principal diye parametre ekliyoruz bunun icerisinde login olmus kullanicilarin bilgileri geliyor biz burda getName ile adini aldik
//		successdto cevabi donsun dedik 

	}
}
