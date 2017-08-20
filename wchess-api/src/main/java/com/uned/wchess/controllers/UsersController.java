package com.uned.wchess.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uned.wchess.api.ErrorAPI;
import com.uned.wchess.exceptions.AccesoNoAutorizadoException;
import com.uned.wchess.exceptions.AuthorizationHeaderException;
import com.uned.wchess.models.User;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
	private HttpHeaders errorHeaders;
	
	public UsersController() {
		errorHeaders = new HttpHeaders();
		errorHeaders.add("WWW-Authenticate","error=\"invalid_token\"");
	}

	@RequestMapping(value="{userId}", method=RequestMethod.GET)
	public ResponseEntity<User> get(@PathVariable long userId){
		User user = new User();
		
		return ResponseEntity.ok(user);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAll(@RequestHeader(value = "Authorization", defaultValue = "", required = false) String authorizationHeader){
		List<User> users = new ArrayList<User>();
		return ResponseEntity.ok(users);
	}	
		
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<User> save(@RequestHeader(value = "Authorization", defaultValue = "", required = false) String authorizationHeader, @RequestBody User user){
		User createdUser = new User();		
		createdUser.setId(2L);
		createdUser.setName("New User");
		
		
		return ResponseEntity.ok(createdUser);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<User> update(@RequestHeader(value = "Authorization", defaultValue = "", required = false) String authorizationHeader, @RequestBody User user){
		User createdUser = new User();
		
		return ResponseEntity.ok(createdUser);
	}
	
	@RequestMapping(value="{userId}", method=RequestMethod.DELETE)
	public ResponseEntity<User> delete(@RequestHeader(value = "Authorization", defaultValue = "", required = false) String authorizationHeader, @PathVariable long userId){
		User createdUser = new User();
		
		return ResponseEntity.ok(createdUser);
	}
		
	private String extraerToken(String authorizationHeader) {
		if (authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		} else {
			throw new AuthorizationHeaderException();
		}
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(HttpMessageNotReadableException e) {
		LOGGER.error("Returning HTTP 400 Bad Request: " + e);
		throw e;
	}
	@ExceptionHandler(AuthorizationHeaderException.class)
	@ResponseBody
	private ResponseEntity<ErrorAPI> handleAuthorizationHeaderException(AuthorizationHeaderException e){
		LOGGER.error("handleAuthorizationHeaderException :: e:{} ", e.getMessage());
		ErrorAPI error = new ErrorAPI();
		error.setCodigo("AUTHORIZATIONHEADEREXCEPTION");
		return new ResponseEntity<ErrorAPI>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AccesoNoAutorizadoException.class)
	@ResponseBody
	private ResponseEntity<ErrorAPI> handleAccesoNoAutorizadoException(AccesoNoAutorizadoException e){
		LOGGER.error("handleAccesoNoAutorizadoException :: e:{} ", e.getMessage());
		ErrorAPI error = new ErrorAPI();
		error.setCodigo("ACCESONOAUTORIZADOEXCEPTION");
		return new ResponseEntity<ErrorAPI>(error, HttpStatus.UNAUTHORIZED);
	}
}