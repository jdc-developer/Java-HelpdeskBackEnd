package jdc.helpdesk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import jdc.helpdesk.entity.Ticket;
import jdc.helpdesk.enums.Priority;
import jdc.helpdesk.enums.Status;

public interface TicketRepository extends CrudRepository<Ticket, Integer>{

	Page<Ticket> findByUserIdOrderByDateDesc(int userId, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityOrderByDateDesc(
			String title, Status status, Priority priority, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndUserIdOrderByDateDesc(
			String title, Status status, Priority priority, int userId, Pageable pages);
	
	Page<Ticket> findByTitleIgnoreCaseContainingAndStatusAndPriorityAndAssignedUserIdOrderByDateDesc(
			String title, Status status, Priority priority, int assignedUserId, Pageable pages);
	
	Page<Ticket> findByNumber(Integer number, Pageable pages);
	
	Page<Ticket> findAll(Pageable pageable);
}
