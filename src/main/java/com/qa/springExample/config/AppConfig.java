package com.qa.springExample.config;

import java.time.LocalTime;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//Tag indicates that the class has a bean definition method 
@Configuration
public class AppConfig {
	
	// 1 and 2 will be created with singelton pattern
	//1
	@Bean // managed object - one that's created by Spring
	public String message() {
		return "Hello, World! " + LocalTime.now();
	}
	//2
	@Bean // bean 2
	public String message2(String message) {
		return message + "Electric boogaloo";
	}
	//3

	@Bean
	@Scope("prototype")
	public ModelMapper mapper() {
		return new ModelMapper();
	}
}
//beans are not not always the best the times to use it is when 
//when working with an external library 
// or if we have lots of config or settings for your project you dont need to.
