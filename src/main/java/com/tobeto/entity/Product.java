package com.tobeto.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "shelves")
@EqualsAndHashCode(exclude = "shelves")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "product_name")
	private String name;

	@Column(name = "min_stock")
	private int minimum;

	@Column(name = "product_desc")
	private String description;

	@Column(name = "image")
	private String image;

	@OneToMany(mappedBy = "product")
	private List<Shelf> shelves;

}
