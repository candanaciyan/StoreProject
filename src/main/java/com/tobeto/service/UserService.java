package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.entity.User;
import com.tobeto.repository.RoleRepository;
import com.tobeto.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User createUser(User user) {
		return userRepository.save(user);
	}
	// genellikle save edilen objenin idsini merak ederiz bu yuzden save islemleri
	// veya update islemlerinde
	// return ediyoruz bunlari saveden gelen objeyi
	// vtninda kaydetme islemlerinde id den dolayi
	// user yaratan metot geriye de user donduruyor ama geriye dondurdugu obje
	// icinde id bilgisi de bulunmus oluyor

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
			}

	@Transactional
	public User addUser(User user) {
		return userRepository.save(user);
	}

	// bu metodu login olma sirasinda kullanacagiz
	// bu metodu login olma sirasinda kullanacagiz
//	userservicetemi burda
//	mi kullnacagiz
//	ona iyi planla burasi daha mantikli gibi
	@Transactional
	public Optional<User> getUser(String email) {// kullanicinin emailine gore arattiricaz
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {// varsa bu kullanici
			user.get().getRole();// bu sayede bu kullanicinin rollerini vtden cekecek icine koyup
									// geri dondurecek
		}
		return user;
	}

	public boolean changePassword(String oldPassword, String newPassword, String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			// kullanıcı, adına göre veritabanında bulundu.
			// şifresini kontrol edelim.
			User oUser = user.get();
			if (passwordEncoder.matches(oldPassword, oUser.getPassword())) {
				// şifresi doğru. Şifresini yeni şifre ile güncelleyelim.
				oUser.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(oUser);
				return true;
			}
		}
		return false;
	}

//	@Transactional
//	public Kullanicilar kayitOl(String email, String password) {
//		if (kullanicilarRepository.findByKullaniciAdi(email).isPresent()) {
//			// yeni kayıt işleminde var olan bir kayıdın email'i verilmiş.
//			// Hata döndürelim.
//			throw new RuntimeException("Bu isimde bir kullanıcı var");
//		}
//		final Kullanicilar kullanicilar = new Kullanicilar();
//		kullanicilar.setKullaniciAdi(email);
//		List<Roller> roller = rollerRepository.findAll();
//		roller = roller.stream().filter(r -> !r.getRol().equals("admin")).toList();
//		// ManytoMany ilişki olduğu için java sınıflarında da iki yönlü ilişki
//		// oluşturmak gerekiyor.
//		kullanicilar.setRollers(roller);
//		roller.forEach(r -> r.getKullanicilars().add(kullanicilar));
//		kullanicilar.setSifre(passwordEncoder.encode(password));
//		return kullanicilarRepository.save(kullanicilar);
//	}
//	

}
