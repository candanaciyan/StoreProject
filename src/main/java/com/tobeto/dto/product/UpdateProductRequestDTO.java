package com.tobeto.dto.product;

import lombok.Data;

@Data
public class UpdateProductRequestDTO {
	private int id;
	private String name;
	private int minimum;
	private String description;
	private String image;
}
