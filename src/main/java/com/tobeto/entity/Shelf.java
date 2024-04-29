package com.tobeto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "product")
@EqualsAndHashCode(exclude = "product")
@Table(name = "shelves")
public class Shelf {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "shelf_count")
	private int count;

	@Column(name = "shelf_capacity")
	private int capacity;

	@ManyToOne
	private Product product;

}
