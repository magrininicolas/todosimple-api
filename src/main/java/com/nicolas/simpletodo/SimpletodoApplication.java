package com.nicolas.simpletodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class SimpletodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpletodoApplication.class, args);
	}

}
