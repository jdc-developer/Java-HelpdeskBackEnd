package jdc.helpdesk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import jdc.helpdesk.entity.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer>{

	Page<Ticket> findByUserIdOrderByDateDesc(String userId, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityOrderByDateDesc(
			String title, String status, String priority, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndUserIdOrderByDateDesc(
			String title, String status, String priority, String userId, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndAssignedUserIdOrderByDateDesc(
			String title, String status, String priority, String assignedId, Pageable pages);
	
	Page<Ticket> findByNumber(Integer number, Pageable pages);
}
