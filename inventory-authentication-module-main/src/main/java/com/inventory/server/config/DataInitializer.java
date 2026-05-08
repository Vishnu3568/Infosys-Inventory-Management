package com.inventory.server.config;

import com.inventory.server.entity.Role;
import com.inventory.server.entity.User;
import com.inventory.server.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create testuser / testpass if it doesn't exist
            System.out.println("DataInitializer: Checking for 'testuser'...");
            if (userRepository.findByUsername("testuser").isEmpty()) {
                System.out.println("DataInitializer: 'testuser' not found. Creating it now...");
                User testUser = User.builder()
                        .username("testuser")
                        .email("testuser@example.com")
                        .password(passwordEncoder.encode("testpass"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(testUser);
                System.out.println("DataInitializer: SUCCESS - Created 'testuser' with password 'testpass' and role ADMIN");
            } else {
                System.out.println("DataInitializer: 'testuser' already exists in the database.");
            }
        };
    }
}
