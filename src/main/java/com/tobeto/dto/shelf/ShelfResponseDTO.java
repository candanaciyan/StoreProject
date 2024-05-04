package com.tobeto.dto.shelf;

import lombok.Data;

@Data
public class ShelfResponseDTO {
	private int id;
	private int count;
	private int capacity;
	private int productId;
	private String productName;

}
