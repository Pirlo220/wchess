package com.uned.wchess.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import com.uned.wchess.models.User;

//http://javasampleapproach.com/spring-framework/spring-boot/how-to-use-jdbc-template-with-spring-boot-for-postgres-database
@Component
public class UsersRepoBD extends JdbcDaoSupport implements UsersRepository {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired
	DataSource dataSource;
	@Autowired
	UsersRsExtractor usersRsExtractor;

	public static final String USERS_COLUMN_ID = "user_id";
	public static final String USERS_COLUMN_USERNAME = "user_username";
	public static final String USERS_COLUMN_EMAIL = "user_email";
	public static final String USERS_COLUMN_NAME = "user_name";
	public static final String USERS_COLUMN_SURNAME = "user_surname";
	public static final String USERS_COLUMN_ELO = "user_elo";

	private final String GET_USERS = "SELECT users.id AS user_id, " + "users.username AS user_username, "
			+ "users.email AS user_email, " + "users.name AS user_name, " + "users.surname AS user_surname, "
			+ "users.elo AS user_elo "
			+ "FROM users WHERE 1 = 1 ";

	private static final String CRITERIA_ID = "AND users.id = :user_id ";
	private static final String CRITERIA_NAME = "AND users.name LIKE :user_name ";
	private static final String CRITERIA_SURNAME = "AND users.surname LIKE :user_surname ";

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public List<User> getUsers(Map<String, Object> criteria) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		String query = GET_USERS;
		if (criteria.containsKey("user_id")) {
			paramMap.addValue("user_id", criteria.get("user_id"));
			query += CRITERIA_ID;
		}
		if (criteria.containsKey("user_name")) {
			paramMap.addValue("user_name", "" + criteria.get("user_name") + "");
			query += CRITERIA_NAME;
		}
		if (criteria.containsKey("user_surname")) {
			paramMap.addValue("user_surname", "%" + criteria.get("user_surname") + "%");
			query += CRITERIA_SURNAME;
		}
		List<User> users = new ArrayList<User>();
		try {
			 jdbcTemplate.query(query, paramMap, usersRsExtractor);
		}catch(Exception e) {
			String ee = "";
		}
		return users;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}