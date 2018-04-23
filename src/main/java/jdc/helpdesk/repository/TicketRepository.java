package jdc.helpdesk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import jdc.helpdesk.entity.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer>{

	Page<Ticket> findByUserIdOrderByDateDesc(int userId, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityOrderByDateDesc(
			String title, String status, String priority, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndUserIdOrderByDateDesc(
			String title, String status, String priority, int userId, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndAssignedUserIdOrderByDateDesc(
			String title, String status, String priority, int assignedUserId, Pageable pages);
	
	Page<Ticket> findByNumber(Integer number, Pageable pages);
	
	Page<Ticket> findAll(Pageable pageable);
}
