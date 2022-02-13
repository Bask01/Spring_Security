package ca.sheridancollege.bask.week9.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.bask.week9.beans.User;
import ca.sheridancollege.bask.week9.database.DatabaseAccess;

@Controller
public class MainController {
	
	//Autowire the database to inject
	@Autowired @Lazy // @Lazy Annotation is required to prevent circular dependency!
	private DatabaseAccess da;
	

	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/secure")
	public String secure(Authentication auth, Model model) { //To Access the authentication info we use @Authentication parameter
		//this will grab the email from the auth that's get name
		String email = auth.getName();
		//grab the list of roles and converting them
		//list of granted authority objects
		List<String> roleList =new ArrayList<String>();		
		for (GrantedAuthority ga : auth.getAuthorities()) {
			roleList.add(ga.getAuthority());
		}
		
		//add the username into the model
		model.addAttribute("username", email);
		//add the roleList into the model
		model.addAttribute("roles", roleList);
		
		return "/secure/index.html";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	
	@GetMapping("/permissionDenied")
	public String permDenined() {
		return "error/permissionDenied.html";
	}
	
	/*
	 * Maps to URL pattern "/register"
	 * returns register form
	 */
	@GetMapping("/register")
	public String loadRegForm() {
		return "register.html";
	}
	
	@GetMapping("/exercises")
	public String goToExercises() {
		return "exercises/index.html";
	}
	
	/*
	 * PostMapping for to URL pattern "/register"
	 * receives email and password as parameters
	 */
	@PostMapping("/register")
	public String processRegForm(@RequestParam String email, @RequestParam String pass) {
		//invoke addUser method in database
		da.addUser(email, pass);
		
		User user = da.findUserAccount(email); // we're doing this to get user ID
		da.addUserRole(user.getUserId(), 1); // here we call the userID! and assigning a role
		
		return "redirect:/login"; //redirect the page after registering!
		
	}
}
