package com.articlemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	//	System.out.println(words_per_minutes);
	}

	
	
	
	/*
	 * @Bean public static PropertySourcesPlaceholderConfigurer
	 * propertyConfigInDev() { return new PropertySourcesPlaceholderConfigurer(); }
	 */
}
