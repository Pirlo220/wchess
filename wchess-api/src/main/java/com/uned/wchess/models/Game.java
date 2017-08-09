package com.uned.wchess.models;

import java.util.Date;

public class Game {
	private long id;
	private Date createdOn;
	private Date datedOn;
	private User owner;
	private long views;
	
	public Game() {
		this.id = -1;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getDatedOn() {
		return datedOn;
	}
	public void setDatedOn(Date datedOn) {
		this.datedOn = datedOn;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public long getViews() {
		return views;
	}
	public void setViews(long views) {
		this.views = views;
	}
	@Override
	public String toString() {
		return "Game [id=" + id + ", createdOn=" + createdOn + ", datedOn=" + datedOn + ", owner=" + owner + ", views="
				+ views + "]";
	}
}
