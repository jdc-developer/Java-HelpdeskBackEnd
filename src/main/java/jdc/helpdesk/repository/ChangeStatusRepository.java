package jdc.helpdesk.repository;

import org.springframework.data.repository.CrudRepository;

import jdc.helpdesk.entity.ChangeStatus;

public interface ChangeStatusRepository extends CrudRepository<ChangeStatus, Integer>{

	Iterable<ChangeStatus> findByTicketIdOrderByDtChangedDesc(int ticketId);
}
