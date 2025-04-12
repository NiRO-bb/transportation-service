package com.github.transportation_service.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
		"com.github.transportation_service.server.controller",
		"com.github.transportation_service.server.repository",
		"com.github.transportation_service.server.service"
})

@SpringBootApplication
public class TransportationServiceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportationServiceServerApplication.class, args);
		System.out.println("Server started!");
	}

}
