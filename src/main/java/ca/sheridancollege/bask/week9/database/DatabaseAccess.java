package ca.sheridancollege.bask.week9.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.bask.week9.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Method to find the user if it is in database
	 * searches for a specific user by their email address
	 * @param email user mail address as a username
	 * @return it will return that user object
	 */	
	public User findUserAccount(String email) {
		
		
		//query to look for a user by a specific email address
		String sql = "SELECT * FROM users WHERE email=:email;";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		//bean property row mapper, will create an arraylist for the users matching with the
		// searched email address
		ArrayList<User> users = (ArrayList<User>)jdbc.query(sql, params,
				new BeanPropertyRowMapper<User>(User.class));
		
		if (users.size() > 0) //if users size is greater than )
			return users.get(0);// it will return the user
		else //returns null if there is no user found
			return null;
					
	}
	
	/**
	 * Method to retrieve the list of roles that the user belongs to.
	 * @param userId method receive a long integer for the userid
	 * @return the roles in type String list
	 */
	public ArrayList<String> getRolesById(long userId) {
		
		
		//sql query to select only the user id field from user role and the role name from the roles table
		//where user-roles roleid is equal to the roles role id
		//and the user-role userid is equal to the userid we passed in.
		String sql = "Select user_role.userid, roles.rolename"
				+ " FROM user_role, roles WHERE user_role.roleid = roles.roleid"
				+ " AND user_role.userid=:userid;";		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("userid", userId); //add the parameter for userid
		
		//return an arraylist of strings where the element values are going to be the names
		//of the roles
		ArrayList<String> roles = new ArrayList<String>();
		//List of maps represents each row in the returned row. 
		//each row contains the role name and the user id
		List<Map<String, Object>> rows = jdbc.queryForList(sql, params);
		for (Map<String, Object> row:rows) {
			roles.add((String)row.get("roleName"));
		}
		
		return roles; //return roles list		
		
	}
	
	/*
	 *  SQL query string to insert into users the email, 
	 *  encrypted password, and the value true for the enabled field.
	 * 
	 */
	public void addUser(String email, String password) {
		
		//SQL query string to insert into users the email, 
		String sql = "INSERT INTO users (email, encryptedpassword, enabled) "
				+ "VALUES (:email, :encPass, true);";
		
		//MapSqlParameterSource to fill in parameters for email and the encrypted password.
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		//add the email and the password as encoded
		params.addValue("email", email).addValue("encPass", passwordEncoder.encode(password));
		
		//Run the query with jdbc.update() and be sure to pass in the parameter source.
		jdbc.update(sql,  params);				
	}
	
	
	/*
	 * Create an SQL query string to insert into user_role the provided user ID and role ID.
	 * Use a MapSqlParameterSource to fill in parameters for the user ID and role ID.
	 */
	public void addUserRole(long userId, long roleId) {
		//SQL query string to insert into user_role the provided user ID and role ID.
		String sql = "INSERT INTO user_role (userId, roleId) VALUES (:user, :role);";
		
		//MapSqlParameterSource to fill in parameters for the user ID and role ID.
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		params.addValue("user", userId).addValue("role", roleId);
		//Run the query with jdbc.update() and be sure to pass in the parameter source.
		jdbc.update(sql, params);
;	}
}
