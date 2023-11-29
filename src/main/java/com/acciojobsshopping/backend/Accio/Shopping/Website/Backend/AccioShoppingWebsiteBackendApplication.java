package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.annotations.EnableSwagger2;



@EnableSwagger2
@Configuration
public class AccioShoppingWebsiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccioShoppingWebsiteBackendApplication.class, args);
	}

}
