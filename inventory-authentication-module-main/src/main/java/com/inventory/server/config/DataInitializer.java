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
            // Create Testuser / Testpass if it doesn't exist
            System.out.println("DataInitializer: Checking for 'Testuser'...");
            if (userRepository.findByUsername("Testuser").isEmpty()) {
                System.out.println("DataInitializer: 'Testuser' not found. Creating it now...");
                User testUser = User.builder()
                        .username("Testuser")
                        .email("test@example.com")
                        .password(passwordEncoder.encode("Testpass"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(testUser);
                System.out.println("DataInitializer: SUCCESS - Created 'Testuser' with password 'Testpass' and role ADMIN");
            } else {
                System.out.println("DataInitializer: 'Testuser' already exists in the database.");
            }
        };
    }
}
