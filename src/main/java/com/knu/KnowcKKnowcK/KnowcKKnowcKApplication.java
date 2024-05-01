package com.knu.KnowcKKnowcK;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class KnowcKKnowcKApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowcKKnowcKApplication.class, args);
	}

}
