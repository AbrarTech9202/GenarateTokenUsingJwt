package com.jwt.configue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SpringSecurityConfigue {
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService()
	{
		UserDetails adminrole= User.builder()
				              .username("admin")
				              .password(passwordEncoder().encode("admin"))
				              .roles("ADMIN")
				              .build();
		

		UserDetails userRole=User.builder()
				             .username("user")
				             .password(passwordEncoder().encode("user"))
				             .roles("USER")
				             .build();
		
		return new InMemoryUserDetailsManager(adminrole,userRole);
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	
	{
		return configuration.getAuthenticationManager();
	}
	
}
