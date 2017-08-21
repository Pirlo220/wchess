package com.uned.wchess.models;

public class GameSearchModel {
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GameSearchModel [id=" + id + "]";
	}
}