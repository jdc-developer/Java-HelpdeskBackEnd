package jdc.helpdesk.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jdc.helpdesk.entity.User;
import jdc.helpdesk.security.jwt.JwtUserFactory;
import jdc.helpdesk.services.UserService;

@Service
public class JwtUserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = service.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Usuário não encontrado com o email '%s'", email));
		} else {
			return JwtUserFactory.create(user);
		}
	}
}
