package com.lotterychecker.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotterychecker.model.Game;
import com.lotterychecker.repository.GameRepository;

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
@Service
public class GameService {
    private static final Logger	LOG = LogManager.getLogger(GameService.class);

    @Autowired
    private GameRepository	repository;
    
    public Game saveGame(Game game) {
	LOG.debug("Entry method saveGame(Game game)");
	
	validGame(game);
	Game savedGame = null;
	try {
	    savedGame = repository.save(game);
	}
	catch (Exception e) {
	    String msg = e.getMessage();
	    LOG.error("Error while trying save game. " + msg);
	    throw new RuntimeException(msg);
	}
	
	LOG.debug("Entry method saveGame(Game game)");
	return savedGame;
	
    }
    
    private void validGame(Game game) {
	LOG.debug("Entry method validGame(Game game)");
	if (game.getName() == null) {
	    String msg = "The field name is required.";
	    LOG.error(msg);
	    throw new RuntimeException(msg);
	}
	
	if (game.getNumberMin() < 0 || game.getNumberMax() < 0) {
	    String msg = "The fields numberMin and numberMax are required.";
	    LOG.error(msg);
	    throw new RuntimeException(msg);
	}
	
	if (game.getNumberMin() > game.getNumberMax()) {
	    String msg = "The field numberMin cannot be higher than numberMax.";
	    LOG.error(msg);
	    throw new RuntimeException(msg);
	}
	LOG.debug("Exit method validGame(Game game)");
    }
    
    public Game getGame(String id) {
	LOG.debug("Entry method getGame(String id)");

	Long gameId = Long.valueOf(id);
	Game game = repository.findById(gameId).orElse(null);

	LOG.debug("game=" + game);
	LOG.debug("Exit method getGame(String id)");
	return game;
    }

    public List<Game> getAllGames() {
	LOG.debug("Entry method  getAllGames()");

	List<Game> games = repository.findAll();
	LOG.debug("Exit method  getAllGames()");
	return games;
    }
}
