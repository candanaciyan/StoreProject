package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Shelf;
import com.tobeto.exception.ServiceException;
import com.tobeto.exception.ServiceException.ERROR_CODES;
import com.tobeto.repository.ShelfRepository;

import jakarta.transaction.Transactional;

@Service
public class ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	public List<Shelf> getAllShelves() {
		return shelfRepository.findAll();
	}

	public int createShelf(int capacity, int count) {
		// public int diyerek geriye kac tane urettigimi dondurebilirim

		if (count > 100) {
			count = 100;
			// 100den fazla box yaratmayalim limit o olsun dedik

		}
		for (int i = 0; i < count; i++) {
			Shelf shelf = new Shelf();
			shelf.setCapacity(capacity);
			shelfRepository.save(shelf);
		}
		return count;

	}

	@Transactional
	public void deleteShelf(int id) {
		Optional<Shelf> oshelf = shelfRepository.findById(id);
		if (oshelf.isPresent()) {
			Shelf shelf = oshelf.get();
			if (shelf.getCount() > 0) {
				throw new ServiceException(ERROR_CODES.SHELF_HAS_PRODUCTS);
			}
			shelfRepository.deleteById(id);
		} else {
			throw new ServiceException(ERROR_CODES.SHELF_NOT_FOUND);
		}

	}

	public void updateShelf(int id, int capacity) {
		Optional<Shelf> oshelf = shelfRepository.findById(id);
		if (oshelf.isPresent()) {
			Shelf shelf = oshelf.get();
			if (capacity < shelf.getCount()) {
				throw new ServiceException(ERROR_CODES.SET_SHELF_COUNT);
			}
			shelf.setCapacity(capacity);
			shelfRepository.save(shelf);
		}
	}

}
