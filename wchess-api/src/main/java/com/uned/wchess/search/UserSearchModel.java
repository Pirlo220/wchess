package com.uned.wchess.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserSearchModel {
	private List<Long> ids;
	private String username;	
	private String name;
	private String surname;
	private int minimumELO;
	private int maximumELO;
	private Map<String, Object> criteria;
	
	public UserSearchModel() {
		this.ids = new ArrayList<Long>();
		this.minimumELO = 0;
		this.maximumELO = 3500;
		this.criteria = new HashMap<String, Object>();
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
		if(ids.size() > 0) {
			this.criteria.put("user_ids", ids);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if(name != null && !name.isEmpty()) {
			this.criteria.put("user_name", name);
		}
	}

	public int getMinimumELO() {
		return minimumELO;
	}

	public void setMinimumELO(int minimumELO) {
		this.minimumELO = minimumELO;
		this.criteria.put("user_minum_elo", minimumELO); 
	}

	public int getMaximumELO() {
		return maximumELO;
	}

	public void setMaximumELO(int maximumELO) {
		this.maximumELO = maximumELO;		
		this.criteria.put("user_maxim_elo", maximumELO);		
	}	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@JsonIgnore
	public Map<String, Object> criteria() {
		return new HashMap<String, Object>(criteria);
	}

	@Override
	public String toString() {
		return "UserSearchModel [ids=" + ids + ", name=" + name + ", minimumELO=" + minimumELO + ", maximumELO="
				+ maximumELO + ", criteria=" + criteria + "]";
	}
}