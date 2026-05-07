package com.inventory.database_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.inventory.database_system.entity")
@EnableJpaRepositories(basePackages = "com.inventory.database_system.repository")
public class DatabaseSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseSystemApplication.class, args);
	}

}
