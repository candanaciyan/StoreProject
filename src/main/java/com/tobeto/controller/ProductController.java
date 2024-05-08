package com.tobeto.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tobeto.dto.SuccessResponseDTO;
import com.tobeto.dto.product.AcceptProductRequestDTO;
import com.tobeto.dto.product.CreateProductRequestDTO;
import com.tobeto.dto.product.DeleteProductRequestDTO;
import com.tobeto.dto.product.ProductCountResponseDTO;
import com.tobeto.dto.product.ProductResponseDTO;
import com.tobeto.dto.product.UpdateProductRequestDTO;
import com.tobeto.entity.Product;
import com.tobeto.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	@Qualifier("requestMapper")
	private ModelMapper requestMapper;

	@Autowired
	@Qualifier("responseMapper")
	private ModelMapper responseMapper;

	@PostMapping("/create")
	public SuccessResponseDTO createProduct(@RequestBody CreateProductRequestDTO dto) {
		Product product = requestMapper.map(dto, Product.class);
		productService.createProduct(product);
		return new SuccessResponseDTO();
	}

	@PostMapping("/delete")
	public SuccessResponseDTO deleteProduct(@RequestBody DeleteProductRequestDTO dto) {
		productService.deleteProduct(dto.getId());
		return new SuccessResponseDTO();
	}

	@PostMapping("/update")
	public SuccessResponseDTO updateProduct(@RequestBody UpdateProductRequestDTO dto) {
		Product product = requestMapper.map(dto, Product.class);
		productService.updateProduct(product);
		return new SuccessResponseDTO();

	}

	@PostMapping("/accept")
	public SuccessResponseDTO acceptProduct(@RequestBody AcceptProductRequestDTO dto) {
		productService.acceptProduct(dto.getProductId(), dto.getCount());
		return new SuccessResponseDTO();
	}

	@PostMapping("/sale")
	public SuccessResponseDTO saleProduct(@RequestBody AcceptProductRequestDTO dto) {
		int count = productService.getProductCount(dto.getProductId());
		if (count < dto.getCount()) {
			return new SuccessResponseDTO("Not enough products");
		} else {
			String message = productService.saleProduct(dto.getProductId(), dto.getCount());
			return new SuccessResponseDTO(message);
		}
	}

	// butun urunler
	@GetMapping("/all")
	public List<ProductResponseDTO> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return products.stream().map(p -> responseMapper.map(p, ProductResponseDTO.class)).toList();
	}

	// idsi verilen urunlerin toplam sayisi
	@GetMapping("/count/{productId}")
	public ProductCountResponseDTO getProductCount(@PathVariable int productId) {
		int count = productService.getProductCount(productId);
		return new ProductCountResponseDTO(count);
	}
}
