package com.uned.wchess.models;

public class User {
	private long id;
	private String username;
	private String email;
	private String name;
	private String surname;
	private int elo;	
	
	public User() {
		this.id = -1;
		this.elo = 1200;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getElo() {
		return elo;
	}
	public void setElo(int elo) {
		this.elo = elo;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}		
}
