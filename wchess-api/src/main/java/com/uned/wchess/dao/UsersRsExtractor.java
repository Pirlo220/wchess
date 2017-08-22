package com.uned.wchess.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.uned.wchess.models.User;

public class UsersRsExtractor implements ResultSetExtractor<List<User>>{
	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<User> users = new ArrayList<User>();
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getLong(""));
			users.add(user);
		}
		return users;
	}
}