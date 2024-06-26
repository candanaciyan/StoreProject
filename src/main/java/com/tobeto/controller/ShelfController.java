package com.tobeto.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.SuccessResponseDTO;
import com.tobeto.dto.shelf.CreateShelfRequestDTO;
import com.tobeto.dto.shelf.DeleteShelfRequestDTO;
import com.tobeto.dto.shelf.ShelfResponseDTO;
import com.tobeto.entity.Shelf;
import com.tobeto.service.ShelfService;

@RestController
@RequestMapping("/api/v1/shelf")
public class ShelfController {
	@Autowired
	private ShelfService shelfService;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@GetMapping("/")
	public List<ShelfResponseDTO> getAllShelves() {
		List<Shelf> shelves = shelfService.getAllShelves();
		return shelves.stream().map(s -> responseMapper.map(s, ShelfResponseDTO.class)).toList();
	}

	@PostMapping("/create")
	public SuccessResponseDTO createShelf(@RequestBody CreateShelfRequestDTO dto) {
		int result = shelfService.createShelf(dto.getCount(), dto.getCapacity());
		return new SuccessResponseDTO(String.valueOf(result));
	}

	@PostMapping("/delete")
	public SuccessResponseDTO deleteShelf(@RequestBody DeleteShelfRequestDTO dto) {
		shelfService.deleteShelf(dto.getId());
		return new SuccessResponseDTO();
	}

}
