package com.fullstack.backendjwt.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fullstack.backendjwt.service.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

	  @Autowired
	  private UserService userService;

	  @Autowired
	  private JwtFilter jwtFilter;
	  
	  @SuppressWarnings({ "removal", "deprecation" })
	  @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		  http
		  .csrf()
		  .disable()
		  .authorizeRequests()
		  .requestMatchers("api/v1/authenticate")
          .permitAll()
          .requestMatchers(HttpMethod.OPTIONS,"/**")
          .permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authenticationProvider(daoAuthenticationProvider())
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	      
		  return http.build();
	  }
	    
	    @Bean
	    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration auth) throws Exception {
	       return auth.getAuthenticationManager();
	    }
	 
	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return NoOpPasswordEncoder.getInstance();
	    }
	    
	    @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() {
	    	DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
	    	dao.setUserDetailsService(userService);
	    	dao.setPasswordEncoder(passwordEncoder());
	    	return dao;
	    }
	    
	    
	    
	
}
