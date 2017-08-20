package com.uned.wchess.services;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.uned.wchess.exceptions.NotValidEmailException;
import com.uned.wchess.exceptions.NotValidPasswordException;
import com.uned.wchess.exceptions.NotValidUserNameException;
import com.uned.wchess.models.User;
import com.uned.wchess.search.UserSearchModel;

@Component
public class UserCtrlServiceImpl implements UserCtrlService {
	private final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	@Override
	public List<User> get(String tokenUUID, UserSearchModel searchModel) {
		/*
		 * LOGGER.trace("getPacientes >> tokenUUID :: {} ", tokenUUID);
		 * LOGGER.trace("getPacientes >> searchModel :: {} ", searchModel); String
		 * authorizationHeader = "Bearer " + Atributos.TOKEN_USUARIO_SANITAS;
		 * List<PacienteDTO> pacientesDTO = llamadaGetPacientes(authorizationHeader,
		 * integracionesURL + GET_PACIENTES_URL, searchModel); List<PacienteSanitasDTO>
		 * pacientesSanitasDTO = new ArrayList<PacienteSanitasDTO>(); for (PacienteDTO
		 * pacienteDTO : pacientesDTO) { pacientesSanitasDTO.add(new
		 * PacienteSanitasDTO(pacienteDTO)); }
		 * LOGGER.trace("getPacientes << tokenUUID :: {}, resultado :: {} ", tokenUUID,
		 * pacientesSanitasDTO); return pacientesSanitasDTO;
		 */
		return null;
	}

	@Override
	public User save(String tokenUUID, User user) {
		User userCreated = new User();
		if (isValidUser(user)) {
			//usersService.save(user);
		}
		return user;
	}

	@Override
	public User update(String tokenUUID, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String tokenUUID, User user) {
		// TODO Auto-generated method stub

	}

	private Boolean isValidUser(User user) {
		Boolean isValid = true;
		isValidEmail(user.getEmail());
		isValidUserName(user.getUsername());
		isValidPassword(user.getPassword());
		return isValid;
	}

	private void isValidEmail(String email) {
		if (email == null || (email.trim().length() < 5 || email.trim().length() > 25)) {
			throw new NotValidEmailException();
		}
		
		if(!validate(email)) {
			throw new NotValidEmailException();
		}
	}

	private boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
	
	private void isValidUserName(String userName) {
		if(userName == null || (userName.trim().length() <6 || userName.trim().length() > 16)) {
			throw new NotValidUserNameException();
		}
	}
	
	private void isValidPassword(String password) {
		if(password == null || (password.trim().length() < 8 || password.trim().length() > 12)) {
			throw new NotValidPasswordException();
		}
	}
}