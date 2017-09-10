package br.com.leonardoz.quantocusta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Bean
	CorsFilter corsFilter() {
		CorsFilter filter = new CorsFilter();
		return filter;
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(corsFilter(), SessionManagementFilter.class).authorizeRequests().anyRequest().permitAll()
				.and().formLogin().and().httpBasic().disable().csrf().disable();
	}

}
