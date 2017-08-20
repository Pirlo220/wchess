package com.uned.wchess.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uned.wchess.exceptions.StorageFileNotFoundException;
import com.uned.wchess.models.Game;
import com.uned.wchess.services.StorageService;

@RestController
@RequestMapping("/api/game")
public class GameController {
	@Autowired
	private StorageService storageService;
	
	@RequestMapping(path="{gameId}", method=RequestMethod.GET)
	public ResponseEntity<Game> get(@PathVariable long gameId){
		Game game = new Game();
		game.setId(100);
		game.setDatedOn(new Date());
		return ResponseEntity.ok(game);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Game> getAll(){
		Game game = new Game();
		game.setId(100);
		game.setDatedOn(new Date());		
		return ResponseEntity.ok(game);
	}
	
	/*@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Game> save(@RequestBody Game game){
		Game createdGame = new Game();
		
		return ResponseEntity.ok(createdGame);
	}*/
	
	@RequestMapping(path="{gameId}", method=RequestMethod.PUT)
	public ResponseEntity<Game> update(@RequestBody Game game, @PathVariable long gameId){
		Game createdGame = new Game();
		
		return ResponseEntity.ok(createdGame);
	}
	
	@RequestMapping(path="{gameId}", method=RequestMethod.DELETE)
	public ResponseEntity<Game> delete(@PathVariable long gameId){
		Game createdGame = new Game();
		
		return ResponseEntity.ok(createdGame);
	}
		
	@RequestMapping(method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
/*
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
	        String uploadedFileLocation) {

	    try {
	        OutputStream out = new FileOutputStream(new File(
	                uploadedFileLocation));
	        int read = 0;
	        byte[] bytes = new byte[1024];

	        out = new FileOutputStream(new File(uploadedFileLocation));
	        while ((read = uploadedInputStream.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	    } catch (IOException e) {

	        e.printStackTrace();
	    }

	   }*/
}