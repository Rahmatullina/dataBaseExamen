package com.DataBaseTask.DataBaseTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DataBaseTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataBaseTaskApplication.class, args);
	}
}
