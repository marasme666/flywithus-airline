package com.flywithus.flights;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@OpenAPIDefinition
public class FlywithusFlightsApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(FlywithusFlightsApplication.class, args);
	}
	
    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion, @Value("${application-name}") String appName) 
    {

     return new OpenAPI()

          .info(new Info()

          .title("Microservice: " + appName.toUpperCase() + " API")

          .version(appVersion)

          .description(appDesciption)

          .termsOfService("http://swagger.io/terms/")

          .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }     
	

}
