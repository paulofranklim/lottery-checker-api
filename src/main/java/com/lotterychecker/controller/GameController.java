package com.lotterychecker.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotterychecker.model.Game;
import com.lotterychecker.service.GameService;

/**
 * <pre>
 * Author         : Paulo Franklim, paulofranklim@hotmail.com
 * Purpose        : <Purpose>
 * Input files    : N/A
 * Log File       : N/A
 * Output file    : N/A
 *
 * Copyright 2020 github.com/paulofranklim
 * </pre>
 */

@RestController
public class GameController {
    private static final Logger	LOG = LogManager.getLogger(GameController.class);
    
    @Autowired
    private GameService		service;
    
    @RequestMapping(value = "save-game", method = RequestMethod.POST)
    public String saveGame(@RequestBody Game game) {
	LOG.debug("Entry method saveGame(@RequestBody Game game)");
	Game savedGame = service.saveGame(game);
	LOG.debug(savedGame.toString());
	LOG.debug("Exit method saveGame(@RequestBody Game game)");
	return "Game '" + savedGame.getName() + "' created. id=" + savedGame.getId();
    }

    @RequestMapping(value = "load-game/{id}", method = RequestMethod.GET)
    public Game loadGame(@PathVariable("id") String id) {
	LOG.debug("Entry method loadGame(@PathVariable(\"id\") String id) ");

	Game game = service.getGame(id);
	LOG.debug("Exit method loadGame(@PathVariable(\"id\") String id)");
	return game;
    }
    
    @RequestMapping(value = "load-games", method = RequestMethod.GET)
    public List<Game> loadAllGames() {
	LOG.debug("Entry method loadAllGames()");

	List<Game> games = service.getAllGames();
	LOG.debug("Exit method loadAllGames()");
	return games;
    }
    
}
