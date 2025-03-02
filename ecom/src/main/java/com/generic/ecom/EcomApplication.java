package com.generic.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EcomApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		System.out.println("Ecom Application Starting .... ");
		SpringApplication.run(EcomApplication.class, args);
		System.out.println("Ecom Application started successfully");
	}
}
