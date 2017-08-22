package com.uned.wchess.dao;

import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.uned.wchess.models.User;
import com.uned.wchess.models.UsersSearchModel;
//http://javasampleapproach.com/spring-framework/spring-boot/how-to-use-jdbc-template-with-spring-boot-for-postgres-database
public class UsersRepoBD extends JdbcDaoSupport implements UsersRepository {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired 
    DataSource dataSource;
	@Autowired
	UsersRsExtractor usersRsExtractor;

	@Override
	public List<User> getUsers(UsersSearchModel searchModel) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		List<User> users = jdbcTemplate.query("", paramMap, usersRsExtractor);
		return users;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}