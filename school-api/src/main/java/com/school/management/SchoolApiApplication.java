package com.school.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApiApplication.class, args);
		System.out.println("School Management System API is running...");
	}

}
