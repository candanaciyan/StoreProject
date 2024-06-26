package com.tobeto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {

	List<Shelf> findAllByProductIdAndCountGreaterThan(int id, int count);

	List<Shelf> findAllByCount(int count);

	@Query("SELECT s FROM Shelf s WHERE s.product.id = :productId and s.count < s.capacity order by s.id limit 1")
	Optional<Shelf> findByProductIdAndNotFull(int productId);
	// o urunun idsi ile onu saklayan shelfi bul ve ona gore kapasitesine bak
	// idsi dusuk olandan aldim

	@Query("SELECT sum(s.count) FROM Shelf s WHERE s.product.id = :productId")
	Integer getProductCount(Integer productId);
	// null degeri geri dondurebilsin diye int degil Integer dondurecek
}
