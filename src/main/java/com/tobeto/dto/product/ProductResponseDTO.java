package com.tobeto.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
	private int id;
	private String name;
	private int quantity;
	private int minimum;
	private String description;
	private String image;
}
