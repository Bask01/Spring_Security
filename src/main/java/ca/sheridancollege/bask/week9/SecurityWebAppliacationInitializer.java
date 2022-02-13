package ca.sheridancollege.bask.week9;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import ca.sheridancollege.bask.week9.security.SecurityConfig;



public class SecurityWebAppliacationInitializer extends AbstractSecurityWebApplicationInitializer {

	
		public SecurityWebAppliacationInitializer() {
			super(SecurityConfig.class);
		}
}
