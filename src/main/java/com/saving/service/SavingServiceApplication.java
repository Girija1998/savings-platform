package com.saving.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SavingServiceApplication {

	public static void main(String[] args) {

		BCryptPasswordEncoder encoder=
				new BCryptPasswordEncoder();

		System.out.println("Admin Password Hash:");
		System.out.println(
				encoder.encode("admin123")
		);
		System.out.println("User Password Hash:");
		System.out.println(
				encoder.encode("123")
		);
		SpringApplication.run(SavingServiceApplication.class, args);
	}

}
