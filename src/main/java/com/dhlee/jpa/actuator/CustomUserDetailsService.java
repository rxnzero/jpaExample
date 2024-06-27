package com.dhlee.jpa.actuator;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dhlee.jpa.exmaple.User;
import com.dhlee.jpa.exmaple.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	logger.info("Attempting to load user: {}", username);
    	Optional<User> op = userRepository.findByUsername(username);
    	User user = null;
    	if(op.isPresent()) {
    		user = op.get();
    	}
        if (user == null) {
        	logger.warn("User not found: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        logger.info("User found: {}", user);
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}