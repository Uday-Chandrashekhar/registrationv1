package com.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication //root class: this is where actual projects starts
public class RegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

}

//We know that @SpringBootApplication is the entry point for the project so we can use this as a configurationclass as well

	/*@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}


	 */