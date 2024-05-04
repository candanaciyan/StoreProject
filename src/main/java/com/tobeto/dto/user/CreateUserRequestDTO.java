package com.tobeto.dto.user;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
	private String name;
	private String surname;
	private String email;
	private String password;
	private int roleId;

}
