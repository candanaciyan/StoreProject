package com.tobeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.entity.Role;
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

	public Role getRole(int id) {
		return roleRepository.findById(id).get();
	}

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

}
