package com.dpetrov.imposter_companion_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ImposterGameCompanionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImposterGameCompanionApplication.class, args);
	}

}
