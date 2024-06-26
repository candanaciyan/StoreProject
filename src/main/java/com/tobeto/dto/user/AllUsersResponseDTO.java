package com.tobeto.dto.user;

import java.util.UUID;

import com.tobeto.entity.Role;

import lombok.Data;

@Data
public class AllUsersResponseDTO {
	private UUID id;;
	private String name;
	private String surname;
	private String email;
	private Role role;

}
