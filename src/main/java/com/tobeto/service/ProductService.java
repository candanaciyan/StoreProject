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

	// eger o id de urun yoksa count 0 olacak
	public int getProductCount(int productId) {
		Integer count = shelfRepository.getProductCount(productId);
		return count == null ? 0 : count;
		// nullsa sifir degilse count icindeki degeri dondur dedik
	}

	public void deleteProduct(int id) {
		productRepository.deleteById(id);

	}

	@Transactional
	public void acceptProduct(int productId, int count) {
		Product product = getProduct(productId);
		Optional<Shelf> oShelf = shelfRepository.findByProductIdAndNotFull(productId);
//		boxrepo uzerinden where kosuluyla kapasitesiyle countu ayni olanlari isteyecegiz
//		customqueery olusturmam lazim buna parametre olarak fruitid verecegim
//		o fruitidyi icinde barindiran ve tam dolu olmayan boxi bulup bana getirecek 
//		bu metot yok bunu olusturacagiz repo icinde

		if (oShelf.isPresent()) {
			// yarı dolu shelf bulundu. İçine aldığı kadar product koyalım.
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
		// kalan product'ler varsa kalan boş shelf'lara doldurulacak.
		if (count > 0) {
			fillEmptyShelf(count, product);
		}

	}

	private void fillEmptyShelf(int count, Product product) {
		List<Shelf> emptyShelves = shelfRepository.findAllByCount(0);
		// countu 0 olan boxlari istedik icinde 0 eleman olan

		int nextFirstEmpty = 0;
		while (count > 0) {
			if (nextFirstEmpty >= emptyShelves.size()) {
				// elimizde doldurabileceğimiz boş shelf kalmadı.
				throw new ServiceException(ERROR_CODES.NOT_ENOUGH_SHELF);
			}
			Shelf shelf = emptyShelves.get(nextFirstEmpty); // ilk boş kutu
			shelf.setProduct(product);
			// konacakmiktar
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
			// yarı dolu shelf bulundu. Satış öncelikli olarak bu shelf içinden yapılacak.
			Shelf shelf = oShelf.get();
			int salesQuantity = count;

			if (salesQuantity > shelf.getCount()) {
				salesQuantity = shelf.getCount();
			}
			shelf.setCount(shelf.getCount() - salesQuantity);
			if (shelf.getCount() == 0) {
				// shelf boşaldı. product ile ilişkisini kaldıralım.
				shelf.setProduct(null);
			}
			shelfRepository.save(shelf);
			count -= salesQuantity;
		}
		// satış yapılacak product'ler kaldı ise diğer tam dolu shelf'lardan satış devam
		// edecek.
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
			// product bulunamadı. hata ver
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
				// Satış yapılacak tam dolu rafta ürün kalmadı
				// dolu shelf kalmadı.
				throw new ServiceException(ERROR_CODES.PRODUCT_NOT_FOUND);
			}
			Shelf shelf = fullShelves.get(nextFirstFullRow); // ilk dolu shelf
			// Düşük id'li raftan başlayarak satış yapar

			int soldQuantity = count;
			if (soldQuantity > shelf.getCount()) {
				soldQuantity = shelf.getCount();
			}
			shelf.setCount(shelf.getCount() - soldQuantity);
			if (shelf.getCount() == 0) {
				// shelf bosaldi.Product ile ilişkisini kaldıralım.

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
