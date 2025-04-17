package com.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;

/*Esta clase tendra toda la configuracion de seguridad del proyecto*/


//configuration es para convertirla en un bean
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	@SuppressWarnings({ "removal" })
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
		http
			
			//para deshabilitar esa seguridad 
			.csrf().disable()
			.cors().and()
			//Vamos a autorizar la peticiones http
			.authorizeHttpRequests()
			
			.requestMatchers("/category/**").hasAnyRole("CUSTOMER","ADMIN")
			//.requestMatchers(HttpMethod.GET,"/category/**").permitAll()
			.requestMatchers(HttpMethod.GET,"/category/**").hasAnyRole("ADMIN","CUSTOMER")
			.requestMatchers(HttpMethod.POST,"/category/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.GET,"/product/*").hasAuthority("random_order")
			.requestMatchers(HttpMethod.GET).hasRole("ADMIN")
			.requestMatchers("product").hasRole("ADMIN")
			
			// Aqui le voy a agregar el autority este es el que voy a permitir @GetMapping("/{id}")
			
			//cualquier peticion que llegue 
			.anyRequest()
			//permitimos todas
			//.permitAll();
			//Indica que debe estar autenticada y por el basic
			.authenticated()
			.and()
			.httpBasic();
			
		
		//Tiene que retornar un SecurityFilterChain  
		return http.build();
		
	}
	
//	@Bean 
//	public UserDetailsService memoryUsers() {
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		
//		
//		
//		
//		UserDetails customer = User.builder()
//				.username("customer")
//				.password(passwordEncoder().encode("customer123"))
//				.roles("CUSTOMER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(admin,customer);
//	}
	
	
	//Se ocupa que esten codificadas nomas para que no esten en texto plano
	
	//Nomas para incluirlo en el ciclo de vida de sprign lo ponemos como Bean 
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
