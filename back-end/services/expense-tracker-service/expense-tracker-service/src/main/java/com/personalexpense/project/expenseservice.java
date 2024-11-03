package com.personalexpense.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class expenseservice {

	public static void main(String[] args) {
		SpringApplication.run(expenseservice.class, args);
	}

}
