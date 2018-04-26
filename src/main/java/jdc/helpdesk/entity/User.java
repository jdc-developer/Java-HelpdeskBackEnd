package jdc.helpdesk.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jdc.helpdesk.enums.Profile;

@Entity
@Table(name="T_HD_USER")
@SequenceGenerator(name="seqUser", sequenceName="SQ_HD_USER", allocationSize=1)
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqUser")
	@Column(name="CD_USER")
	private int id;
	
	@Column(name="DS_EMAIL", nullable=false, length=250)
	private String email;
	
	@Column(name="DS_PASSWORD", nullable=false, length=250)
	private String password;
	
	@Column(name="DS_PROFILE", nullable=false)
	private Profile profile;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> tickets;
	
	@OneToMany(mappedBy="assignedUser", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> technicianTickets;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<ChangeStatus> changes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public List<Ticket> getTechnicianTickets() {
		return technicianTickets;
	}

	public void setTechnicianTickets(List<Ticket> technicianTickets) {
		this.technicianTickets = technicianTickets;
	}

	public List<ChangeStatus> getChanges() {
		return changes;
	}

	public void setChanges(List<ChangeStatus> changes) {
		this.changes = changes;
	}
}
