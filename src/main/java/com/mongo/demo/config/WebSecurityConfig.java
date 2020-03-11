package com.mongo.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mongo.demo.service.CustomUserDetailsService;
import com.mongo.demo.service.TokenAuthenticationService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired 
	private TokenAuthenticationService tokenService;
	
	@Autowired
	private CustomUserDetailsService userService;
	
	@Bean
	public UserDetailsService mongoUserDetails() {
	    return new CustomUserDetailsService();
	}
	
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    UserDetailsService userDetailsService = mongoUserDetails();
	    auth
	        .userDetailsService(userDetailsService)
	        .passwordEncoder(bCryptPasswordEncoder);

	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
	      .authorizeRequests().antMatchers("/login").permitAll()
	      .antMatchers(HttpMethod.OPTIONS,"/login").permitAll()
	      .anyRequest().authenticated()
	      .and().httpBasic()
	      .and().addFilterBefore(new CorsFilter(),ChannelProcessingFilter.class)
	      // We filter the api/login requests
	      /*.addFilterBefore(new JWTLoginFilter("/login", authenticationManager(),tokenService),
	                UsernamePasswordAuthenticationFilter.class)*/
	        // And filter other requests to check the presence of JWT in header
	        .addFilterBefore(new JWTAuthenticationFilter(tokenService),
	                UsernamePasswordAuthenticationFilter.class);
	}
	
}
