package com.tobeto.dto.user;

import lombok.Data;

@Data
public class PasswordChangeAdminRequestDTO {
	private String email;
	private String newPassword;

}
