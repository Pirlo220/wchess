package com.uned.wchess.dao;

import java.util.List;
import com.uned.wchess.models.User;
import com.uned.wchess.models.UsersSearchModel;

public interface UsersRepository {
	List<User> getUsers(UsersSearchModel searchModel);
	User save(User user);
}