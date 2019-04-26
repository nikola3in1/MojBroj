package com.nikola2934.MojBroj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.nikola2934")
public class MojBrojApplication {
	public static void main(String[] args) {
		SpringApplication.run(MojBrojApplication.class, args);
	}
}
