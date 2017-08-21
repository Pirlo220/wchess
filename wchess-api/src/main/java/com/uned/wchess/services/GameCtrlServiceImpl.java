package com.uned.wchess.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.uned.wchess.exceptions.NotValidExtensionException;
import com.uned.wchess.exceptions.NoFreeSpaceAvailableException;
import com.uned.wchess.models.Game;
import com.uned.wchess.models.GameSearchModel;

@Component
public class GameCtrlServiceImpl implements GameCtrlService{
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
	public void upload(String tokenUUID, MultipartFile file) {		
		String extension = getFileExtension(file);
		validateExtension(extension);
		validateFreeDisk(file);		
		storageService.store(file);
	}
	
	private void validateFreeDisk(MultipartFile file) {		
		long freeSpace = new File("c:/").getFreeSpace();
		if((freeSpace - file.getSize()) < MINIMUM_FREE_SPACE) {
			throw new NoFreeSpaceAvailableException();
		}
	}

	private String getFileExtension(MultipartFile file) {
		String[] fileFrags = file.getOriginalFilename().split("\\.");
		String extension = fileFrags[fileFrags.length-1];
		return extension;
	}
	
	private void validateExtension(String extension) {
		if(!extension.toLowerCase().trim().equals("pgn")) {
			throw new NotValidExtensionException();
		}
	}
	
}