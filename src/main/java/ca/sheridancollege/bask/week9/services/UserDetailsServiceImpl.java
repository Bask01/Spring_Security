package ca.sheridancollege.bask.week9.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.bask.week9.database.DatabaseAccess;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	@Autowired @Lazy // @Lazy Annotation is required to prevent circular dependency!
	private DatabaseAccess da;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		ca.sheridancollege.bask.week9.beans.User user = da.findUserAccount(username);
		if (user == null) {
			
			System.out.printf("User not found: %s%n", username);
			throw new UsernameNotFoundException("User" + username + "not found in database.");
		}
		List<String> roleList = da.getRolesById(user.getUserId());
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		
		if (roleList != null) {
			for (String role : roleList)
				grantList.add(new SimpleGrantedAuthority(role));
		}
		
		UserDetails userDetails = new User(user.getEmail(), user.getEncryptedPassword(),
				grantList);
		return userDetails;
	}

		
}
