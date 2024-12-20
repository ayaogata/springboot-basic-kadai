package com.example.postingapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*
 * ログインルール(認証・認可の制御)の定義クラス
 * */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
			//どのページへのアクセスを許可するのか
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/css/**", "/signup/**").permitAll()
				.anyRequest().authenticated()
			)
			//ログイン時
			.formLogin((form) -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/posts?loggedIn")
				.failureUrl("/login?error")
				.permitAll()
				//.defaultSuccessUrl("/?loggedIn")
			)
			//ログアウト時
			.logout((logout) -> logout
				//.logoutSuccessUrl("/login")
				.logoutSuccessUrl("/login?loggedOut")
				.permitAll()
			);
		
		return http.build();
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
