package com.uned.wchess.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.uned.wchess.exceptions.ModificationFileException;
import com.uned.wchess.exceptions.NoFreeSpaceAvailableException;
import com.uned.wchess.exceptions.NotValidExtensionException;
import com.uned.wchess.models.Game;
import com.uned.wchess.models.GameSearchModel;

import sun.misc.BASE64Encoder;

@Component
public class GameCtrlServiceImpl implements GameCtrlService {
	private static final long MINIMUM_FREE_SPACE = 100000;
	@Autowired
	private StorageService storageService;

	@Override
	public Game save(String tokenUUID, Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> get(String tokenUUID, GameSearchModel game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String tokenUUID, Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	public Game update(String tokenUUID, Game game) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void upload(String tokenUUID, MultipartFile file) throws NoSuchAlgorithmException, IOException {
		String hash = generateSHA256HashValue(file);
		String extension = getFileExtension(file);
		validateExtension(extension);
		validateFreeDisk(file);		
		storageService.store(file);
	}

	private void validateFreeDisk(MultipartFile file) {
		long freeSpace = new File("c:/").getFreeSpace();
		if ((freeSpace - file.getSize()) < MINIMUM_FREE_SPACE) {
			throw new NoFreeSpaceAvailableException();
		}
	}

	private String getFileExtension(MultipartFile file) {
		String[] fileFrags = file.getOriginalFilename().split("\\.");
		String extension = fileFrags[fileFrags.length - 1];
		return extension;
	}

	private void validateExtension(String extension) {
		if (!extension.toLowerCase().trim().equals("pgn")) {
			throw new NotValidExtensionException();
		}
	}

	private String generateSHA256HashValue(MultipartFile file) throws IOException, NoSuchAlgorithmException {
		byte[] buffer = new byte[8192];
		int count;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		InputStream bis = file.getInputStream();// new BufferedInputStream(new FileInputStream(fileName));
		while ((count = bis.read(buffer)) > 0) {
			digest.update(buffer, 0, count);
		}
		byte[] hash = digest.digest();

		return new BASE64Encoder().encode(hash);
	}
	
	@Override
	public void download(String tokenUUID, Game game) throws NoSuchAlgorithmException, IOException {		
		MultipartFile file = generateGameFile(game);
		checkStoredHash(game, file);		
	}

	private void checkStoredHash(Game game, MultipartFile file) throws NoSuchAlgorithmException, IOException {		
		Game originalGame = new Game();//gameService.get(game);
		String currentHash = generateSHA256HashValue(file);				
		if(!originalGame.getHash().equals(currentHash)){
			throw new ModificationFileException();
		}
	}
	
	private MultipartFile generateGameFile(Game game) {
		Path path = Paths.get("/my/server/location/" + game.getId() + ".pgn");
		String name = game.getId() + ".pgn";
		String contentType = "text/plain";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		return new MockMultipartFile(name, name, contentType, content);		
	}

}