package com.uned.wchess.services;

import java.util.List;

import com.uned.wchess.models.User;
import com.uned.wchess.search.UserSearchModel;

public interface UserCtrlService {
	List<User> get(UserSearchModel searchModel);
	User save(User user);
	User update(User user);
	void delete(User user);
}