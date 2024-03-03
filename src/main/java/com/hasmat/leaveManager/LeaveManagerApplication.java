package com.hasmat.leaveManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hasmat.leaveManager.repository")
@EnableScheduling
public class LeaveManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaveManagerApplication.class, args);
	}

}
