package jdc.helpdesk.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import jdc.helpdesk.entity.User;

@Component
public interface UserService {

	User findByEmail(String email);
	
	User createOrUpdate(User user);
	
	Optional<User> findById(int id);
	
	void delete(int id);
	
	Page<User> findAll(int page, int count);
}
