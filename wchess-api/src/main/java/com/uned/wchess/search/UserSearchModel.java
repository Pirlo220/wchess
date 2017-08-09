package com.uned.wchess.search;

import java.util.ArrayList;
import java.util.List;

public class UserSearchModel {
	private List<Long> ids;
	private String name;
	private int minimumELO;
	private int maximumELO;
	
	public UserSearchModel() {
		this.ids = new ArrayList<Long>();
		this.minimumELO = 0;
		this.maximumELO = 3500;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinimumELO() {
		return minimumELO;
	}

	public void setMinimumELO(int minimumELO) {
		this.minimumELO = minimumELO;
	}

	public int getMaximumELO() {
		return maximumELO;
	}

	public void setMaximumELO(int maximumELO) {
		this.maximumELO = maximumELO;
	}

	@Override
	public String toString() {
		return "UserSearchModel [ids=" + ids + ", name=" + name + ", minimumELO=" + minimumELO + ", maximumELO="
				+ maximumELO + "]";
	}
}