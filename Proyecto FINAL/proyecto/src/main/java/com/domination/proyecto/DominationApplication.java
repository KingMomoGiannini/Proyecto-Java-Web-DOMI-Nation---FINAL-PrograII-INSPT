package com.domination.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.domination.proyecto.repositories")
@EntityScan(basePackages = "com.domination.proyecto.models")
public class DominationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominationApplication.class, args);
	}

}
