package com.cybertek.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

	@Bean
	@Qualifier("weather-WebClient")
	public WebClient webClient_Weather(@Value("${weather.api.url}") String url, @Value("${weather.api.zip}") String zip,
			@Value("${weather.api.country}") String country, @Value("${weather.api.units}") String units,
			@Value("${weather.api.apikey}") String apikey) {

		String baseUrl = String.format("%s?zip=%s,%s&units=%s&appid=%s", url, zip, country, units, apikey);
		return WebClient.create(baseUrl);
	}

	@Bean
	@Qualifier("restService-WebClient")
	public WebClient webClientRest(@Value("${rest.api.url}") String url) {
		return WebClient
		        .builder()
		        .baseUrl(url)
		        .build();
		        
	}
	


}
