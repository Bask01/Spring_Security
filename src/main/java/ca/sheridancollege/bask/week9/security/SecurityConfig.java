package ca.sheridancollege.bask.week9.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ca.sheridancollege.bask.week9.services.UserDetailsServiceImpl;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
		@Autowired
		private LogAccessDeniedHandler accessDeniedHandler;
		
		@Autowired UserDetailsServiceImpl userDetailsService;
		
		
		@Override
		public void configure(HttpSecurity http) throws Exception {
			
			
			http.csrf().disable(); 
			http.headers().frameOptions().disable();
			
			//configure authorization for all of the resources in the secure directory
			//to users with the user role
			//antMatcher method accepts variable lenght argument lsists of strings representing
			//the patterns that you wanna apply a constraint to 
			http.authorizeRequests().antMatchers("/secure/**").hasRole("USER")
			//.antMatchers(HttpMethod.POST, "/processForm").permitAll()//only allow when it is a post request
					//.antMatchers("/exercises/**").hasRole("USER") only permit when it is a post method
					.antMatchers("/", "/js/**", "/css/**", "/images/**", "/register").permitAll()//allow anyone to access these folders
					.antMatchers("/h2-console/**").permitAll()
					.anyRequest().authenticated()
				.and()
					.formLogin().loginPage("/login").defaultSuccessUrl("/secure", true).permitAll()//configure the login
				.and()
					.logout()
						.invalidateHttpSession(true)
						.clearAuthentication(true)
					    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					    .logoutSuccessUrl("/login?logout").permitAll()	
				.and()
					.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
			
		}
		
		@Bean 
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
				
		@Override 	
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());					
		}
		
}

