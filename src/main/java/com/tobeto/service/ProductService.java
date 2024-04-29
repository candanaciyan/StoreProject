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
		return productRepository.findAll();
	}

	public void deleteProduct(int id) {
		productRepository.deleteById(id);

	}

	@Transactional
	public void acceptProduct(int productId, int count) {
		Product product = getProduct(productId);
		Optional<Shelf> oShelf = shelfRepository.findByProductIdNotFull(productId);
		if (oShelf.isPresent()) {
			// yarı dolu shelf bulundu. İçine aldığı kadar product koyalım.
			Shelf shelf = oShelf.get();
			int konacakMiktar = count;
			int shelfIcindeKalanKisim = shelf.getCapacity() - shelf.getCount();
			if (konacakMiktar > shelfIcindeKalanKisim) {
				konacakMiktar = shelfIcindeKalanKisim;
			}
			shelf.setCount(shelf.getCount() + konacakMiktar);
			shelfRepository.save(shelf);
			count -= konacakMiktar;
		}
		// kalan product'ler varsa kalan boş shelf'lara doldurulacak.
		if (count > 0) {
			bosShelfDoldur(count, product);
		}

	}

	public String saleProduct(int productId, int count) {
		String message = "";
		Product product = getProduct(productId);
		Optional<Shelf> oShelf = shelfRepository.findByProductIdNotFull(productId);
		if (oShelf.isPresent()) {
			// yarı dolu shelf bulundu. Satış öncelikli olarak bu shelf içinden yapılacak.
			Shelf shelf = oShelf.get();
			int satisMiktari = count;

			if (satisMiktari > shelf.getCount()) {
				satisMiktari = shelf.getCount();
			}
			shelf.setCount(shelf.getCount() - satisMiktari);
			if (shelf.getCount() == 0) {
				// boş boşaldı. Fruit ile ilişkisini kaldıralım.
				shelf.setProduct(null);
			}
			shelfRepository.save(shelf);
			count -= satisMiktari;
		}
		// satış yapılacak product'ler kaldı ise diğer tam dolu shelf'lardan satış devam
		// edecek.
		if (count > 0) {
			tamDoluShelflerdenSatisYap(count, product);
		}

		if (product.getQuantity() < product.getMinimum()) {

			message = "Urun adedi(" + product.getQuantity() + ") limitin (" + product.getMinimum()
					+ ") altina Dustu.";
		}
		return message;
	}

	public int getProductCount(int productId) {
		Integer count = shelfRepository.getProductCount(productId);
		return count == null ? 0 : count;
	}

	private void bosShelfDoldur(int count, Product product) {
		List<Shelf> emptyShelves = shelfRepository.findAllByCount(0);
		int siradakiIlkBosSirasi = 0;
		while (count > 0) {
			if (siradakiIlkBosSirasi >= emptyShelves.size()) {
				// elimizde doldurabileceğimiz boş shelf kalmadı.
				throw new ServiceException(ERROR_CODES.NOT_ENOUGH_SHELF);
			}
			Shelf shelf = emptyShelves.get(siradakiIlkBosSirasi); // ilk boş kutu
			shelf.setProduct(product);
			int konacakMiktar = count;
			if (konacakMiktar > shelf.getCapacity()) {
				konacakMiktar = shelf.getCapacity();
			}
			shelf.setCount(konacakMiktar);
			shelfRepository.save(shelf);
			count -= konacakMiktar;
			siradakiIlkBosSirasi++;
		}
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

	private void tamDoluShelflerdenSatisYap(int count, Product product) {
		List<Shelf> fullShelves = shelfRepository
				.findAllByProductIdAndCountGreaterThan(product.getId(), 0);
		int siradakiIlkDoluSirasi = fullShelves.size() - 1;
		while (count > 0) {
			if (siradakiIlkDoluSirasi < 0) {
				// elimizde satış yapabileğimiz dolu shelf kalmadı.
				throw new ServiceException(ERROR_CODES.PRODUCT_NOT_FOUND);
			}
			Shelf shelf = fullShelves.get(siradakiIlkDoluSirasi); // ilk dolu shelf

			int satilacakMiktar = count;
			if (satilacakMiktar > shelf.getCount()) {
				satilacakMiktar = shelf.getCount();
			}
			shelf.setCount(shelf.getCount() - satilacakMiktar);
			if (shelf.getCount() == 0) {
				// boş boşaldı. Product ile ilişkisini kaldıralım.
				shelf.setProduct(null);
			}
			shelfRepository.save(shelf);
			count -= satilacakMiktar;
			siradakiIlkDoluSirasi--;
		}
	}
}
