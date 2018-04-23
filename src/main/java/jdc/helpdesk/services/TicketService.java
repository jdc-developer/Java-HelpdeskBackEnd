package jdc.helpdesk.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import jdc.helpdesk.entity.ChangeStatus;
import jdc.helpdesk.entity.Ticket;

@Component
public interface TicketService {

	Ticket createOrUpdate(Ticket ticket);
	
	Optional<Ticket> findById(int id);
	
	void delete(int id);
	
	Page<Ticket> listTicket(int page, int count);
	
	ChangeStatus createChangeStatus(ChangeStatus changeStatus);
	
	Iterable<ChangeStatus> listChangeStatus(int ticketId);
	
	Page<Ticket> findByCurrentUser(int page, int count, int userId);
	
	Page<Ticket> findByParameters(int page, int count, String title, String status, String priority);
	
	Page<Ticket> findByParametersAndCurrentUser(int page, int count, String title, String status, String priority, int userId);
	
	Page<Ticket> findByNumber(int page, int count, Integer number);
	
	Iterable<Ticket> findAll();
	
	Page<Ticket> findByParameterAndAssignedUser(int page, int count, String title, String status, String priority, int assignedUserId);
}
