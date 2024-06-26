package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Product;
import com.tobeto.entity.Shelf;
import com.tobeto.exception.ServiceException;
import com.tobeto.exception.ServiceException.ERROR_CODES;
import com.tobeto.repository.ProductRepository;
import com.tobeto.repository.ShelfRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShelfRepository shelfRepository;

	public Product createProduct(Product product) {
		return productRepository.save(product);

	}

	public List<Product> getAllProducts() {
		List<Product> products = productRepository.findAll();
		for (Product product : products) {
			product.setTotalAmount(getProductCount(product.getId()));
		}
		return products;
	}

	public int getProductCount(int productId) {
		Integer count = shelfRepository.getProductCount(productId);
		return count == null ? 0 : count;
	}

	public void deleteProduct(int id) {
		productRepository.deleteById(id);

	}

	@Transactional
	public void acceptProduct(int productId, int count) {
		Product product = getProduct(productId);
		Optional<Shelf> oShelf = shelfRepository.findByProductIdAndNotFull(productId);

		if (oShelf.isPresent()) {

			Shelf shelf = oShelf.get();
			int placedAmount = count;
			int remainingInside = shelf.getCapacity() - shelf.getCount();
			if (placedAmount > remainingInside) {
				placedAmount = remainingInside;
			}
			shelf.setCount(shelf.getCount() + placedAmount);
			shelfRepository.save(shelf);
			count -= placedAmount;
		}

		if (count > 0) {
			fillEmptyShelf(count, product);
		}

	}

	private void fillEmptyShelf(int count, Product product) {
		List<Shelf> emptyShelves = shelfRepository.findAllByCount(0);

		int nextFirstEmpty = 0;
		while (count > 0) {
			if (nextFirstEmpty >= emptyShelves.size()) {

				throw new ServiceException(ERROR_CODES.NOT_ENOUGH_SHELF);
			}
			Shelf shelf = emptyShelves.get(nextFirstEmpty); // ilk boş kutu
			shelf.setProduct(product);

			int placedAmount = count;
			if (placedAmount > shelf.getCapacity()) {
				placedAmount = shelf.getCapacity();
			}
			shelf.setCount(placedAmount);
			shelfRepository.save(shelf);
			count -= placedAmount;
			nextFirstEmpty++;
		}
	}

	@Transactional
	public String saleProduct(int productId, int count) {
		String message = "";
		Product product = getProduct(productId);
		Optional<Shelf> oShelf = shelfRepository.findByProductIdAndNotFull(productId);
		if (oShelf.isPresent()) {

			Shelf shelf = oShelf.get();
			int salesQuantity = count;

			if (salesQuantity > shelf.getCount()) {
				salesQuantity = shelf.getCount();
			}
			shelf.setCount(shelf.getCount() - salesQuantity);
			if (shelf.getCount() == 0) {

				shelf.setProduct(null);
			}
			shelfRepository.save(shelf);
			count -= salesQuantity;
		}

		if (count > 0) {
			saleF​​romFullLoadedShelves(count, product);
		}

		if (shelfRepository.getProductCount(productId) != null
				&& shelfRepository.getProductCount(productId) < product.getMinimum()) {

			message = "The number of products fell below the limit (" + product.getMinimum() + ").";
		}
		return message;
	}

	private Product getProduct(int productId) {
		Optional<Product> oProduct = productRepository.findById(productId);
		Product product = null;
		if (oProduct.isPresent()) {
			product = oProduct.get();
		} else {

			throw new ServiceException(ERROR_CODES.PRODUCT_NOT_FOUND);
		}
		return product;
	}

	private void saleF​​romFullLoadedShelves(int count, Product product) {
		List<Shelf> fullShelves = shelfRepository
				.findAllByProductIdAndCountGreaterThan(product.getId(), 0);
		int nextFirstFullRow = 0;
		// Düşük id'li raftan başlamak için 0 olarak ayarladık
		while (count > 0) {
			// count 0dan buyuk oldugu surece
			if (nextFirstFullRow >= fullShelves.size()) {

				throw new ServiceException(ERROR_CODES.PRODUCT_NOT_FOUND);
			}
			Shelf shelf = fullShelves.get(nextFirstFullRow);

			int soldQuantity = count;
			if (soldQuantity > shelf.getCount()) {
				soldQuantity = shelf.getCount();
			}
			shelf.setCount(shelf.getCount() - soldQuantity);
			if (shelf.getCount() == 0) {
				shelf.setProduct(null);
			}
			shelfRepository.save(shelf);
			count -= soldQuantity;
			nextFirstFullRow++;
		}
	}

	public void updateProduct(Product product) {
		productRepository.save(product);
	}
}
