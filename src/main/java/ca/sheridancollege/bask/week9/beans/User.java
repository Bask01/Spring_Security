package ca.sheridancollege.bask.week9.beans;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long userId;
	@NonNull private String email;
	@NonNull private String encryptedPassword;
	private boolean enabled;
	
		
}