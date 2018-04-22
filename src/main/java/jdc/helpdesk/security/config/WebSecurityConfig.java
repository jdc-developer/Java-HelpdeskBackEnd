package jdc.helpdesk.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jdc.helpdesk.security.jwt.JwtAuthenticationEntryPoint;
import jdc.helpdesk.security.jwt.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	private UserDetailsService service;
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(this.service).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAuthenticationTokenFilter authFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.authorizeRequests()
			.antMatchers(
					HttpMethod.GET,
					"/",
					"/*.html",
					"/favicon.ico",
					"/**/*.html",
					"/**/*.css",
					"/**/*.js"
				).permitAll()
			.antMatchers("/api/auth/**").permitAll()
			.anyRequest().authenticated();
		security.addFilterBefore(authFilterBean(), UsernamePasswordAuthenticationFilter.class);
		security.headers().cacheControl();
	}
}
