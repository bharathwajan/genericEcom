package com.generic.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        exclude = {
                org.springframework.grpc.autoconfigure.client.GrpcClientAutoConfiguration.class,
                org.springframework.grpc.autoconfigure.server.GrpcServerAutoConfiguration.class
        }
)
//@EnableFeignClients
@EnableDiscoveryClient
public class EcomApplication  {
	public static void main(String[] args) {
		System.out.println("Ecom Application Starting .... ");
		SpringApplication.run(EcomApplication.class, args);
		System.out.println("Ecom Application started successfully");
	}
}
