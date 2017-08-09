package com.uned.wchess.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uned.wchess.models.Game;

@RestController
public class GameController {
	
	@RequestMapping(path="/game/{gameId}", method=RequestMethod.GET)
	public ResponseEntity<Game> get(@PathVariable long gameId){
		Game game = new Game();
		
		return ResponseEntity.ok(game);
	}
	
	@RequestMapping(path="/game/", method=RequestMethod.GET)
	public ResponseEntity<Game> getAll(){
		Game game = new Game();
		
		return ResponseEntity.ok(game);
	}
	
	@RequestMapping(path="/game/", method=RequestMethod.POST)
	public ResponseEntity<Game> save(@RequestBody Game game){
		Game createdGame = new Game();
		
		return ResponseEntity.ok(createdGame);
	}
	
	@RequestMapping(path="/game/{gameId}", method=RequestMethod.PUT)
	public ResponseEntity<Game> update(@RequestBody Game game, @PathVariable long gameId){
		Game createdGame = new Game();
		
		return ResponseEntity.ok(createdGame);
	}
	
	@RequestMapping(path="/game/{gameId}", method=RequestMethod.DELETE)
	public ResponseEntity<Game> delete(@PathVariable long gameId){
		Game createdGame = new Game();
		
		return ResponseEntity.ok(createdGame);
	}
}