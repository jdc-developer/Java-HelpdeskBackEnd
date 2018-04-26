package jdc.helpdesk.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jdc.helpdesk.enums.Priority;
import jdc.helpdesk.enums.Status;

@Entity
@Table(name="T_HD_TICKET")
@SequenceGenerator(name="seqTicket", sequenceName="SQ_HD_TICKET", allocationSize=1)
public class Ticket {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seqTicket")
	@Column(name="CD_TICKET")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="CD_USER")
	private User user;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_CREATED", nullable=false)
	private Calendar date;
	
	@Column(name="CD_TITLE", nullable=false, length=200)
	private String title;
	
	@Column(name="NR_NUMBER", nullable=false)
	private Integer number;
	
	@Column(name="DS_STATUS", nullable=false)
	private Status status;
	
	@Column(name="DS_PRIORITY", nullable=false)
	private Priority priority;
	
	@ManyToOne
	@JoinColumn(name="CD_TECHNICIAN")
	private User assignedUser;
	
	@Column(name="DS_DESCRIPTION", nullable=false, length=2000)
	private String description;
	
	@Column(name="DS_IMAGE")
	private byte[] image;
	
	@OneToMany(mappedBy="ticket", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	private List<ChangeStatus> changes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<ChangeStatus> getChanges() {
		return changes;
	}

	public void setChanges(List<ChangeStatus> changes) {
		this.changes = changes;
	}
}
