package ca.sheridancollege.bask.week9.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LogAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
	
		//SecurityContext allows you to access the settings of security parts of your application
		//Authentication object holds all of the info about the currently authenticated user
		//getContext method return a security context and on 
		//getAuthentication method gets settings on the getContext.return an authentication object.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) { //if there is no an authenticated user//throw a null pointer exception.
			System.out.printf("%s was trying to access protected resources\n%s",
					auth.getName(), request.getRequestURI());//return as a string what the user trying to access.			
			
		}
		//take the response object, pass a string for the location where you want the user to go
		//(we want it to redirect to the permissionDeniedpage )
		response.sendRedirect(request.getContextPath() + "/permissionDenied");
		
		
	}

}
