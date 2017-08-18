package com.uned.wchess.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uned.wchess.models.User;
import com.uned.wchess.search.UserSearchModel;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@RequestMapping(value="{userId}", method=RequestMethod.GET)
	public ResponseEntity<User> get(@PathVariable long userId){
		User user = new User();
		
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAll(){
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setId(1L);
		user.setName("Test");
		users.add(user);
		return ResponseEntity.ok(users);
	}	
		
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<User> save(@RequestBody User user){
		User createdUser = new User();
		
		createdUser.setId(2L);
		createdUser.setName("New User");
		
		
		return ResponseEntity.ok(createdUser);
	}
	
	@RequestMapping(value="{userId}", method=RequestMethod.PUT)
	public ResponseEntity<User> update(@RequestBody User user, @PathVariable long userId){
		User createdUser = new User();
		
		return ResponseEntity.ok(createdUser);
	}
	
	@RequestMapping(value="{userId}", method=RequestMethod.DELETE)
	public ResponseEntity<User> delete(@PathVariable long userId){
		User createdUser = new User();
		
		return ResponseEntity.ok(createdUser);
	}
}