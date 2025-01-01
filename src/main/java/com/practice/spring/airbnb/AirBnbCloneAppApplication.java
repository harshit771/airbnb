package com.practice.spring.airbnb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirBnbCloneAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirBnbCloneAppApplication.class, args);
	}

}
