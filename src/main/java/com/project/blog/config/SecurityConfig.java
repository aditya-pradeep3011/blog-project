package com.project.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.blog.repo.UserRepository;
import com.project.blog.security.BlogUserDetailsService;
import com.project.blog.security.JwtAuthenticationFilter;
import com.project.blog.service.AuthenticationService;

@Configuration
public class SecurityConfig {
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService)
	{
		return new JwtAuthenticationFilter(authenticationService);
	}

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception
	{
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/category/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/post/draft").authenticated()
				.requestMatchers(HttpMethod.GET, "/api/v1/post/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/v1/tag/**").permitAll()
				.anyRequest().authenticated()
		)
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
    
    @Bean
    PasswordEncoder passwordEncoder()
    {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth)
    {
    	return auth.getAuthenticationManager();
    }
    
    @Bean
    UserDetailsService getUserDetailsService(UserRepository userRepository)
    {
    	return new BlogUserDetailsService(userRepository);
    }
}
