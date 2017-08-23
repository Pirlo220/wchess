package com.uned.wchess.dao;

import static com.uned.wchess.dao.UsersRepoBD.USERS_COLUMN_EMAIL;
import static com.uned.wchess.dao.UsersRepoBD.USERS_COLUMN_ID;
import static com.uned.wchess.dao.UsersRepoBD.USERS_COLUMN_NAME;
import static com.uned.wchess.dao.UsersRepoBD.USERS_COLUMN_SURNAME;
import static com.uned.wchess.dao.UsersRepoBD.USERS_COLUMN_USERNAME;
import static com.uned.wchess.dao.UsersRepoBD.USERS_COLUMN_ELO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.uned.wchess.models.User;

@Component
public class UsersRsExtractor implements ResultSetExtractor<List<User>>{
	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<User> users = new ArrayList<User>();
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getLong(USERS_COLUMN_ID));
			user.setUsername(rs.getString(USERS_COLUMN_USERNAME));
			user.setEmail(rs.getString(USERS_COLUMN_EMAIL));
			user.setName(rs.getString(USERS_COLUMN_NAME));
			user.setSurname(rs.getString(USERS_COLUMN_SURNAME));
			user.setElo(rs.getInt(USERS_COLUMN_ELO));
			users.add(user);
		}
		return users;
	}
}