package com.project.admin.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.project.admin")
@EnableJpaRepositories(basePackages = "com.project.admin.dao")
@EntityScan(basePackages = "com.project.admin.entity")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
