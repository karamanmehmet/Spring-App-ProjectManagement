package com.cybertek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.cybertek.converter.RoleDtoConverter;

@SpringBootApplication
public class SpringMvcProjectManagementApplication {



	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringMvcProjectManagementApplication.class, args);
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}



}
