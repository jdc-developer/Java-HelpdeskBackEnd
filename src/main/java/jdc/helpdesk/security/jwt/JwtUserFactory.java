package jdc.helpdesk.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jdc.helpdesk.entity.User;
import jdc.helpdesk.enums.Profile;

public class JwtUserFactory {

	private JwtUserFactory() {}
	
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(), 
				mapToGrantedAutorities(user.getProfile()));
	}
	
	private static List<GrantedAuthority> mapToGrantedAutorities(Profile profile) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profile.toString()));
		return authorities;	
	}
}
