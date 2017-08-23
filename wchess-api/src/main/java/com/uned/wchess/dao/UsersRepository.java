package com.uned.wchess.dao;

import java.util.List;
import java.util.Map;

import com.uned.wchess.models.User;

public interface UsersRepository {
	User save(User user);
	List<User> getUsers(Map<String, Object> criterios);
}