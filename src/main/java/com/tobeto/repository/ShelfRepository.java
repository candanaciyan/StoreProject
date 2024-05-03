package com.tobeto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {

	List<Shelf> findAllByProductIdAndCountGreaterThan(int id, int count);

	List<Shelf> findAllByCount(int count);

	@Query("SELECT s FROM Shelves s WHERE s.product.id = :productId and s.count < s.capacity")
	Optional<Shelf> findByProductIdNotFull(int productId);

	@Query("SELECT sum(s.count) FROM Shelves s WHERE s.product.id = :productId")
	Integer getProductCount(int productId);
}
