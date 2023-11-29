package com.acciojobsshopping.backend.Accio.Shopping.Website.Backend;

import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;



@OpenAPIDefinition(info = @Info(title = "Accio Shopping Website Documentation", version = "1.0", description = "It contains all the endpoint details regarding Accio Shopping website Project",contact = @Contact(name = "Somendra", url = "https://github.com/somendrat22/MyShopping", email = "tiwarisomendra22@gmail.com")))
@SpringBootApplication
public class AccioShoppingWebsiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccioShoppingWebsiteBackendApplication.class, args);
	}

}


