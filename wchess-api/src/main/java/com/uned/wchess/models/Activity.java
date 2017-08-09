package com.uned.wchess.models;

import java.util.Date;

public class Activity {
	private long id;
	private Date dated;
	private String message;
	
	public Activity() {
		this.id = -1;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDated() {
		return dated;
	}
	public void setDated(Date dated) {
		this.dated = dated;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Activity [id=" + id + ", dated=" + dated + ", message=" + message + "]";
	}
}
