package com.tobeto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponseDTO {
	private String message;
	// bu genel response cevabi icin olusturulan dto
	// global seylerde ozel birsey dondurmeyecveksem

}
