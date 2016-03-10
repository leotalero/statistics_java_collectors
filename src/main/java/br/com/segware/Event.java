package br.com.segware;

import java.util.Date;

public class Event {

	private  String sequential_code;
	private String customer_code;
	private String event_code;
	private String event_type;
	private Date start_date;
	private Date end_date;
	private String person_id;
	private Long attendance;
	private Tipo event_type_tipo;
	
	
	public String getSequential_code() {
		return sequential_code;
	}
	public void setSequential_code(String sequential_code) {
		this.sequential_code = sequential_code;
	}
	public String getCustomer_code() {
		return customer_code;
	}
	public void setCustomer_code(String customer_code) {
		this.customer_code = customer_code;
	}
	public String getEvent_code() {
		return event_code;
	}
	public void setEvent_code(String event_code) {
		this.event_code = event_code;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getPerson_id() {
		return person_id;
	}
	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}
	public Long getAttendance() {
		return attendance;
	}
	public void setAttendance(Long attendance) {
		this.attendance = attendance;
	}
	public Tipo getEvent_type_tipo() {
		return event_type_tipo;
	}
	public void setEvent_type_tipo(Tipo event_type_tipo) {
		this.event_type_tipo = event_type_tipo;
	}
	
	
		
	
}
