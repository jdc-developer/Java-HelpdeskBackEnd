package jdc.helpdesk.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jdc.helpdesk.entity.ChangeStatus;
import jdc.helpdesk.entity.Ticket;
import jdc.helpdesk.repository.ChangeStatusRepository;
import jdc.helpdesk.repository.TicketRepository;
import jdc.helpdesk.services.TicketService;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private TicketRepository ticketRep;
	
	@Autowired
	private ChangeStatusRepository changeRep;
	
	@Override
	public Ticket createOrUpdate(Ticket ticket) {
		return this.ticketRep.save(ticket);
	}

	@Override
	public Optional<Ticket> findById(int id) {
		return this.ticketRep.findById(id);
	}

	@Override
	public void delete(int id) {
		this.ticketRep.deleteById(id);
	}

	@Override
	public Page<Ticket> listTicket(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRep.findAll(pages);
	}

	@Override
	public ChangeStatus createChangeStatus(ChangeStatus changeStatus) {
		return this.changeRep.save(changeStatus);
	}

	@Override
	public Iterable<ChangeStatus> listChangeStatus(int ticketId) {
		return this.changeRep.findByTicketIdOrderByDtChangedDesc(ticketId);
	}

	@Override
	public Page<Ticket> findByCurrentUser(int page, int count, int userId) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRep.findByUserIdOrderByDateDesc(userId, pages);
	}

	@Override
	public Page<Ticket> findByParameters(int page, int count, String title, String status, String priority) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRep.findByTitleIgnoreCaseContainingAndStatusAndPriorityOrderByDateDesc(title, status, priority, pages);
	}

	@Override
	public Page<Ticket> findByParametersAndCurrentUser(int page, int count, String title, String status,
			String priority, int userId) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRep.findByTitleIgnoreCaseContainingAndStatusAndPriorityAndUserIdOrderByDateDesc(title, status, priority, userId, pages);
	}

	@Override
	public Page<Ticket> findByNumber(int page, int count, Integer number) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRep.findByNumber(number, pages);
	}

	@Override
	public Iterable<Ticket> findAll() {
		return this.ticketRep.findAll();
	}

	@Override
	public Page<Ticket> findByParameterAndAssignedUser(int page, int count, String title, String status,
			String priority, int assignedUserId) {
		Pageable pages = PageRequest.of(page, count);
		return this.ticketRep.findByTitleIgnoreCaseContainingAndStatusAndPriorityAndAssignedUserIdOrderByDateDesc(title, status, priority, assignedUserId, pages);
	}

}
