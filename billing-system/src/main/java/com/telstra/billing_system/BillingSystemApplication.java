package com.telstra.billing_system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class BillingSystemApplication implements CommandLineRunner {
	@Value("${react.origin}")
    private String frontendOrigin;
	@Override
	public void run(String... args) throws Exception{
		System.out.println(frontendOrigin);
	}
	public static void main(String[] args) {
		SpringApplication.run(BillingSystemApplication.class, args);
		
		System.out.println("hello world");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
