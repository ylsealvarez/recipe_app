package com.recipe.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RecipeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeAppApplication.class, args);
	}

}
