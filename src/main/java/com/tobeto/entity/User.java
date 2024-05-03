package com.tobeto.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "user")
@ToString(exclude = "role")
@EqualsAndHashCode(exclude = "role")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "registration_date")
	private LocalDateTime createDate;
	// disabled gelecek frontendden tarihi frontend oto girecek yani

	@Column(name = "update_date")
	private LocalDateTime updateDate;
	// bu sadece admin icin

	@Column(name = "update_desc")
	private String desc;

	// bu sadece admin icin
	@PrePersist
	public void prePersist() {
		createDate = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		updateDate = LocalDateTime.now();
	}

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

}