package com.tobeto.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalExceptionDTO {
	// bu client tarafina hata durumunda donecek olan cevabi iletecek dto oluyor

	private int code;
	private String mesaj;
}
