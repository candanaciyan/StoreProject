package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Shelf;
import com.tobeto.exception.ServiceException;
import com.tobeto.exception.ServiceException.ERROR_CODES;
import com.tobeto.repository.ShelfRepository;

@Service
public class ShelfService {

	@Autowired
	private ShelfRepository shelfRepository;

	public List<Shelf> getAllShelves() {
		return shelfRepository.findAll();
	}

	public int createShelf(int capacity, int count) {
		if (count > 5) {
			count = 5;
		}
		for (int i = 0; i < count; i++) {
			Shelf shelf = new Shelf();
			shelf.setCapacity(capacity);
			shelfRepository.save(shelf);
		}
		return count;
	}

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

//	public List<Shelf> getShelves(int id) {
//		return shelfRepository.findById(id);
//	}

}
