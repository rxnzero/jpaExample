package com.dhlee.jpa.custom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiUserService {
	@Autowired
    private CustomUserRepository customUserRepository;
	
	public List<CustomUser> getAllUsers() {
        return customUserRepository.listAll();
    }
    public Optional<CustomUser> getUserById(Long id) {
        return Optional.ofNullable(customUserRepository.findUserById(id));
    }

    public CustomUser createUser(CustomUser user) {
        return customUserRepository.createUser(user);
    }

    public void deleteUser(Long id) {
    	customUserRepository.deleteUser(id);
    }
}
