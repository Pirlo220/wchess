package com.uned.wchess.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.uned.wchess.models.Game;
import com.uned.wchess.models.GameSearchModel;

public interface GameCtrlService {
	Game save(String tokenUUID, Game game);
	List<Game> get(String tokenUUID, GameSearchModel game);
	void delete(String tokenUUID, Game game);
	Game update(String tokenUUID, Game game);
	void upload(String tokenUUID, MultipartFile file);
}