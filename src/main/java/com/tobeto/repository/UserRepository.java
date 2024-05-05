package com.tobeto.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tobeto.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> deleteByEmail(String email);

	Optional<User> findByEmail(String email);// emaile gore arayip bulacak

	Optional<User> findByName(String name);
}
