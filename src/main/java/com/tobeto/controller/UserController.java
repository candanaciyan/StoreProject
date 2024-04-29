package com.tobeto.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
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

//	@GetMapping
//	public List<PersonelSorgulaResponseDTO> personelSorgular() {
//		return personelService.getPersoneller().stream()
//				.map(p -> responseMapper.map(p, PersonelSorgulaResponseDTO.class)).toList();
//	}
//	@PostMapping("/yazilimIlanVer")
//	public ResponseEntity<SuccessResponseDTO> yazilimIlanVer(@RequestBody YazilimIlanVerRequestDTO dto) {
//		YazilimIlan yazilimIlan = requestMapper.map(dto, YazilimIlan.class);
//		ilanService.yazilimIlanVer(yazilimIlan);
//		return ResponseEntity.ok(new SuccessResponseDTO("ilan oluşturuldu"));
//	}bunu kayit formunda kullanabilirsin

//	@GetMapping("/yazilimIlanlari")
//	public ResponseEntity<List<TumYazilimIlanlarResponseDTO>> yazilimIlanlari() {
//		List<YazilimIlan> tumIlanlar = ilanService.getTumYazilimIlanlari();
//		List<TumYazilimIlanlarResponseDTO> sonuc = new ArrayList<>();
//		tumIlanlar.forEach(ilan -> {
//			sonuc.add(responseMapper.map(ilan, TumYazilimIlanlarResponseDTO.class));
//		});
//
//		return ResponseEntity.ok(sonuc);bunu tum kullanicilari isterken kullanabilirsin

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
