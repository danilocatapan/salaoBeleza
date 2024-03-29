package br.com.catapan.salaobeleza.infrastructure.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean //controle de instancia, caso precise do authenticationSuccessHandler
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandlerImpl();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			 .csrf().disable()
			.authorizeRequests()
			.antMatchers("/images/**", "/css/**", "/js/**", "/public/**", "/sbpay/**").permitAll()
			.antMatchers("/cliente/**").hasRole(Role.CLIENTE.toString())
			.antMatchers("/profissional/**").hasRole(Role.PROFISSIONAL.toString())
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.failureUrl("/login-error")
				.successHandler(authenticationSuccessHandler())
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.permitAll();
	}
}
