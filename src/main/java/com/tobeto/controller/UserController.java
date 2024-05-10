package com.tobeto.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.SuccessResponseDTO;
import com.tobeto.dto.user.AllUsersResponseDTO;
import com.tobeto.dto.user.CreateUserRequestDTO;
import com.tobeto.dto.user.DeleteUserRequestDTO;
import com.tobeto.dto.user.PasswordChangeAdminRequestDTO;
import com.tobeto.dto.user.PasswordChangeRequestDTO;
import com.tobeto.entity.Role;
import com.tobeto.entity.User;
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
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/create")
	public ResponseEntity<SuccessResponseDTO> createUser(@RequestBody CreateUserRequestDTO dto) {
		Optional<User> oUser = userService.getUser(dto.getEmail());
		if (oUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} else {
			User user = new User();
			user.setName(dto.getName());
			user.setSurname(dto.getSurname());
			user.setEmail(dto.getEmail());
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			Role role = userService.getRole(dto.getRoleId());
			user.setRole(role);
			userService.createUser(user);
			return ResponseEntity.ok(new SuccessResponseDTO("User Created."));
		}
	}

	@PostMapping("/delete")
	public SuccessResponseDTO deleteUser(@RequestBody DeleteUserRequestDTO dto) {
		userService.deleteUser(dto.getEmail());
		return new SuccessResponseDTO();
	}

	@GetMapping("/all")
	public ResponseEntity<List<AllUsersResponseDTO>> getAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		List<AllUsersResponseDTO> sonuc = new ArrayList<>();
		allUsers.forEach(user -> {
			sonuc.add(responseMapper.map(user, AllUsersResponseDTO.class));
		});

		return ResponseEntity.ok(sonuc);
	}

	@PostMapping("/changepassword")
	public ResponseEntity<SuccessResponseDTO> changePassword(
			@RequestBody PasswordChangeRequestDTO dto, Principal principal) {
		boolean result = userService.changePassword(dto.getOldPassword(), dto.getNewPassword(),
				principal.getName());

		if (result) {
			return ResponseEntity.ok(new SuccessResponseDTO("Password Changed."));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PostMapping("/passwordadmin")
	public ResponseEntity<SuccessResponseDTO> changePasswordAdmin(
			@RequestBody PasswordChangeAdminRequestDTO dto) {
		boolean result = userService.changePasswordAdmin(dto.getNewPassword(), dto.getEmail());

		if (result) {
			return ResponseEntity.ok(new SuccessResponseDTO("Password Changed By Admin."));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
