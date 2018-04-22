package jdc.helpdesk.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jdc.helpdesk.entity.User;
import jdc.helpdesk.security.jwt.JwtAuthenticationRequest;
import jdc.helpdesk.security.jwt.JwtTokenUtil;
import jdc.helpdesk.security.model.CurrentUser;
import jdc.helpdesk.services.UserService;

@RestController
@CrossOrigin(origins="*")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/api/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authRequest) throws AuthenticationException {
		
		final Authentication auth = authManager.authenticate(
			new UsernamePasswordAuthenticationToken(
					authRequest.getEmail(),
					authRequest.getPassword()
			)
		);
		SecurityContextHolder.getContext().setAuthentication(auth);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		final User user = userService.findByEmail(authRequest.getEmail());
		user.setPassword(null);
		return ResponseEntity.ok(new CurrentUser(token, user));
	}
}
