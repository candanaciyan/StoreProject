package com.tobeto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tobeto.entity.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {

	List<Shelf> findAllByProductIdAndCountGreaterThan(int id, int count);

	// countu 0 olan boxlari istedik icinde 0 eleman olan
	List<Shelf> findAllByCount(int count);

	@Query("SELECT s FROM Shelf s WHERE s.product.id = :productId and s.count < s.capacity")
	Optional<Shelf> findByProductIdAndNotFull(int productId);
	// capacitysi tam dolu olmayanlar demek bu
	// o urunun idsi ile onu saklayan shelfi bul ve ona gore kapasitesine bak

	@Query("SELECT sum(s.count) FROM Shelf s WHERE s.product.id = :productId")
	Integer getProductCount(int productId);
}
