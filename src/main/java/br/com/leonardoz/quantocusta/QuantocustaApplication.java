package br.com.leonardoz.quantocusta;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class QuantocustaApplication extends WebMvcConfigurerAdapter {

	@Value("${cors.origin}")
	private String origin;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.maxAge(3600)
			.allowedHeaders("Content-type","Authorization")
			.allowedMethods("*")
			.allowCredentials(true)
			.allowedOrigins(origin);
	}

	public static void main(String[] args) {
		SpringApplication.run(QuantocustaApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

}
