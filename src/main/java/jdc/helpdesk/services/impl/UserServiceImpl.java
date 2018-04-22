package jdc.helpdesk.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jdc.helpdesk.entity.User;
import jdc.helpdesk.repository.UserRepository;
import jdc.helpdesk.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository rep;
	
	@Override
	public User findByEmail(String email) {
		return this.rep.findByEmailIgnoreCaseContaining(email);
	}

	@Override
	public User createOrUpdate(User user) {
		return this.rep.save(user);
	}

	@Override
	public Optional<User> findById(int id) {
		return this.rep.findById(id);
	}

	@Override
	public void delete(int id) {
		this.rep.deleteById(id);
	}

	@Override
	public Page<User> findAll(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.rep.findAll(pages);
	}

}
