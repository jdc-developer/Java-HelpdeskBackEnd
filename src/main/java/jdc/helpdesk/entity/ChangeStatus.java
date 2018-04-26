package jdc.helpdesk.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jdc.helpdesk.enums.Status;

@Entity
@Table(name="T_HD_CHANGE_STATUS")
@SequenceGenerator(name="seqStatus", sequenceName="SQ_HD_CHANGE_STATUS", allocationSize=1)
public class ChangeStatus {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqStatus")
	@Column(name="CD_CHANGE_STATUS")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="CD_TICKET")
	@JsonIgnore
	private Ticket ticket;
	
	@ManyToOne
	@JoinColumn(name="CD_USER")
	private User user;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_CHANGED")
	private Calendar dtChanged;
	
	@Column(name="DS_STATUS")
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getDtChanged() {
		return dtChanged;
	}

	public void setDtChanged(Calendar dtChanged) {
		this.dtChanged = dtChanged;
	}
}
