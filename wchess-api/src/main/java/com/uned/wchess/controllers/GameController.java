package com.uned.wchess.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uned.wchess.exceptions.AuthorizationHeaderException;
import com.uned.wchess.exceptions.StorageFileNotFoundException;
import com.uned.wchess.models.Game;
import com.uned.wchess.services.GameCtrlService;

@RestController
@RequestMapping("/api/game")
public class GameController {

	@Autowired
	private GameCtrlService gameCtrlService;

	@RequestMapping(path = "{gameId}", method = RequestMethod.GET)
	public ResponseEntity<Game> get(@PathVariable long gameId) {
		Game game = new Game();
		game.setId(100);
		game.setDatedOn(new Date());
		return ResponseEntity.ok(game);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Game> getAll() {
		Game game = new Game();
		game.setId(100);
		game.setDatedOn(new Date());
		return ResponseEntity.ok(game);
	}

	/*
	 * @RequestMapping(method=RequestMethod.POST) public ResponseEntity<Game>
	 * save(@RequestBody Game game){ Game createdGame = new Game();
	 * 
	 * return ResponseEntity.ok(createdGame); }
	 */

	@RequestMapping(path = "{gameId}", method = RequestMethod.PUT)
	public ResponseEntity<Game> update(@RequestBody Game game, @PathVariable long gameId) {
		Game createdGame = new Game();

		return ResponseEntity.ok(createdGame);
	}

	@RequestMapping(path = "{gameId}", method = RequestMethod.DELETE)
	public ResponseEntity<Game> delete(@PathVariable long gameId) {
		Game createdGame = new Game();

		return ResponseEntity.ok(createdGame);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Game> uploadFile(@RequestHeader(value = "Authorization", defaultValue = "", required = false) String authorizationHeader, @RequestParam("file") MultipartFile file) {
		try {
			gameCtrlService.upload(extraerToken(authorizationHeader), file);
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(new Game());
	}

	@RequestMapping(path = "download/{gameId}", method = RequestMethod.POST)
	public void downloadFile(@RequestHeader(value = "Authorization", defaultValue = "", required = false) String authorizationHeader, @RequestBody Game game) {
		try {
			gameCtrlService.download(extraerToken(authorizationHeader), game);
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
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
		// LOGGER.error("Returning HTTP 400 Bad Request: " + e);
		throw e;
	}
	/*
	 * // save uploaded file to new location private void writeToFile(InputStream
	 * uploadedInputStream, String uploadedFileLocation) {
	 * 
	 * try { OutputStream out = new FileOutputStream(new File(
	 * uploadedFileLocation)); int read = 0; byte[] bytes = new byte[1024];
	 * 
	 * out = new FileOutputStream(new File(uploadedFileLocation)); while ((read =
	 * uploadedInputStream.read(bytes)) != -1) { out.write(bytes, 0, read); }
	 * out.flush(); out.close(); } catch (IOException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
}