package jdc.helpdesk.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jdc.helpdesk.entity.ChangeStatus;
import jdc.helpdesk.entity.Ticket;
import jdc.helpdesk.entity.User;
import jdc.helpdesk.enums.Profile;
import jdc.helpdesk.enums.Status;
import jdc.helpdesk.response.Response;
import jdc.helpdesk.security.jwt.JwtTokenUtil;
import jdc.helpdesk.services.TicketService;
import jdc.helpdesk.services.UserService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins="*")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CLIENT')")
	public ResponseEntity<Response<Ticket>> create(HttpServletRequest request, @RequestBody Ticket ticket,
			BindingResult result) {
		Response<Ticket> response = new Response<Ticket>();
		try {
			validateCreateTicket(ticket, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			ticket.setStatus(Status.NEW);
			ticket.setUser(userFromRequest(request));
			ticket.setDate(Calendar.getInstance());
			ticket.setNumber(generateNumber());
			Ticket ticketPersisted = (Ticket) ticketService.createOrUpdate(ticket);
			response.setData(ticketPersisted);
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateCreateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getTitle() == null) {
			result.addError(new ObjectError("Ticket", "Título obrigatório"));
		}
	}
	
	private User userFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String email = jwtTokenUtil.getUsernameFromToken(token);
		return userService.findByEmail(email);
	}
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
	
	@PutMapping
	@PreAuthorize("hasAnyRole('CLIENT')")
	public ResponseEntity<Response<Ticket>> update(HttpServletRequest request, @RequestBody Ticket ticket,
			BindingResult result) {
		Response<Ticket> response = new Response<Ticket>();
		try {
			validateUpdateTicket(ticket, result);
			if(result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Optional<Ticket> ticketCurrent = ticketService.findById(ticket.getId());
			ticket.setStatus(ticketCurrent.get().getStatus());
			ticket.setUser(ticketCurrent.get().getUser());
			ticket.setDate(ticketCurrent.get().getDate());
			ticket.setNumber(ticketCurrent.get().getNumber());
			if(ticketCurrent.get().getAssignedUser() != null) {
				ticket.setAssignedUser(ticketCurrent.get().getAssignedUser());
			}
			Ticket ticketPersisted = (Ticket) ticketService.createOrUpdate(ticket);
			response.setData(ticketPersisted);
		} catch(Exception e) {
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateUpdateTicket(Ticket ticket, BindingResult result) {
		if(ticket.getId() == 0) {
			result.addError(new ObjectError("Ticket", "Id não informado"));
		}
		if(ticket.getTitle() == null) {
			result.addError(new ObjectError("Ticket", "Título obrigatório"));
		}
	}
	
	@GetMapping(value="{id}")
	@PreAuthorize("hasAnyRole('CLIENT', 'TECHNICIAN')")
	public ResponseEntity<Response<Ticket>> findById(@PathVariable("id") int id) {
		Response<Ticket> response = new Response<Ticket>();
		Optional<Ticket> ticket = ticketService.findById(id);
		if(ticket == null) {
			response.getErrors().add("Não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		List<ChangeStatus> changes = new ArrayList<ChangeStatus>();
		Iterable<ChangeStatus> changesCurrent = ticketService.listChangeStatus(ticket.get().getId());
		for(Iterator<ChangeStatus> iterator = changesCurrent.iterator(); iterator.hasNext();) {
			ChangeStatus changeStatus = (ChangeStatus) iterator.next();
			changeStatus.setTicket(null);
			changes.add(changeStatus);
		}
		ticket.get().setChanges(changes);
		response.setData(ticket.get());
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value="{id}")
	@PreAuthorize("hasAnyRole('CLIENT')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") int id) {
		Response<String> response = new Response<String>();
		Optional<Ticket> ticket = ticketService.findById(id);
		if(ticket == null) {
			response.getErrors().add("Não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		ticketService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	@GetMapping(value="{page}/{count}")
	@PreAuthorize("hasAnyRole('CLIENT', 'TECHNICIAN')")
	public ResponseEntity<Response<Page<Ticket>>> findAll(HttpServletRequest request, @PathVariable("page") int page, @PathVariable("count") int count) {
		Response<Page<Ticket>> response = new Response<Page<Ticket>>();
		Page<Ticket> tickets = null;
		User userRequest = userFromRequest(request);
		if(userRequest.getProfile().equals(Profile.ROLE_TECHINICIAN)) {
			tickets = ticketService.listTicket(page, count);
		} else if (userRequest.getProfile().equals(Profile.ROLE_CLIENT)) {
			tickets = ticketService.findByCurrentUser(page, count, userRequest.getId());
		}
		response.setData(tickets);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value="{page}/{count}/{number}/{title}/{status}/{priority}/{assignedUser}")
	@PreAuthorize("hasAnyRole('CLIENT', 'TECHNICIAN')")
	public ResponseEntity<Response<Page<Ticket>>> findByParameters(HttpServletRequest request, 
			@PathVariable("page") int page, 
			@PathVariable("count") int count,
			@PathVariable("number") int number,
			@PathVariable("title") String title,
			@PathVariable("status") String status,
			@PathVariable("priority") String priority,
			@PathVariable("assignedUser") boolean assignedUser) {
		title = title.equals("uninformed") ? "" : title;
		status = status.equals("uninformed") ? "" : status;
		priority = priority.equals("uninformed") ? "" : priority;
		
		Response<Page<Ticket>> response = new Response<Page<Ticket>>();
		Page<Ticket> tickets = null;
		if(number > 0) {
			tickets = ticketService.findByNumber(page, count, number);
		} else {
			User userRequest = userFromRequest(request);
			if(userRequest.getProfile().equals(Profile.ROLE_TECHINICIAN)) {
				if(assignedUser) {
					tickets = ticketService.findByParameterAndAssignedUser(page, count, title, status, priority, userRequest.getId());
				} else {
					tickets = ticketService.findByParameters(page, count, title, status, priority);
				}
			} else if(userRequest.getProfile().equals(Profile.ROLE_CLIENT)) {
				tickets = ticketService.findByParametersAndCurrentUser(page, count, title, status, priority, userRequest.getId());
			}
		}
		response.setData(tickets);
		return ResponseEntity.ok(response);
	}
}
