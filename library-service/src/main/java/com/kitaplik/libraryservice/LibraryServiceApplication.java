package com.kitaplik.libraryservice;

import com.kitaplik.libraryservice.client.RetrieveMessageErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Library Rest Service API",
				version = "v1"
		)
)
public class LibraryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}

	@Bean
	public ErrorDecoder errorDecoder(){
		return new RetrieveMessageErrorDecoder();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

}
