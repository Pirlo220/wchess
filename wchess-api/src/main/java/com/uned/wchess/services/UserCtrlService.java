package com.uned.wchess.services;

import java.util.List;

import com.uned.wchess.models.User;
import com.uned.wchess.search.UserSearchModel;

public interface UserCtrlService {
	List<User> get(String tokenUUID, UserSearchModel searchModel);
	User save(String tokenUUID, User user);
	User update(String tokenUUID, User user);
	void delete(String tokenUUID, User user);	
}