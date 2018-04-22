package jdc.helpdesk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import jdc.helpdesk.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	User findByEmailIgnoreCaseContaining(String email);
	
	Page<User> findAll(Pageable pageable);
}
