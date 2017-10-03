package br.com.leonardoz.quantocusta;

import java.util.concurrent.Executor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAsync
public class QuantocustaApplication extends WebMvcConfigurerAdapter {

	@Value("${cors.origin}")
	private String origin;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").maxAge(3600).allowedHeaders("Content-type", "Authorization").allowedMethods("*")
				.allowCredentials(true).allowedOrigins(origin);
	}

	public static void main(String[] args) {
		SpringApplication.run(QuantocustaApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("EnviarEmail-");
		executor.initialize();
		return executor;
	}

}
