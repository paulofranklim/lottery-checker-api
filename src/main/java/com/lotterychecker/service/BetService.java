package com.lotterychecker.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotterychecker.model.Bet;
import com.lotterychecker.model.Game;
import com.lotterychecker.repository.BetRepository;
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
public class BetService {
    private static final Logger	LOG = LogManager.getLogger(BetService.class);

    @Autowired
    private BetRepository	repository;

    @Autowired
    private GameRepository	gameRepository;

    public Bet saveBet(Bet bet) {
	LOG.debug("Entry method saveBet(Bet bet)");
	Bet savedBet = null;

	validBet(bet);

	try {
	    savedBet = repository.save(bet);
	}
	catch (Exception e) {
	    String msg = e.getMessage();
	    LOG.error("Error while trying save bet. " + msg);
	    throw new RuntimeException(msg);
	}

	LOG.debug("Exit method saveBet(Bet bet)");
	return savedBet;
    }

    private void validBet(Bet bet) {
	LOG.debug("Entry method validBet(Bet bet)");
	
	Game game = gameRepository.findById(bet.getGameId()).orElse(null);
	if (game != null) {
	    
	    String[] numbers = bet.getNumbers().split(",");
	    int max = game.getLastPossibleNumber();

	    for (String eachNumber : numbers) {
		if (Integer.valueOf(eachNumber).compareTo(max) > 0) {
		    String msg = "The bet number should in a range : 0 and " + max;
		    LOG.error(msg);
		    throw new RuntimeException(msg);
		}
	    }
	    
	    if (numbers.length < game.getNumberMin() || numbers.length > game.getNumberMax()) {
		String msg = "The bet must to have at least " + game.getNumberMin() + " and maximum " + game.getNumberMax() + " numbers.";
		LOG.error(msg);
		throw new RuntimeException(msg);
		
	    }
	} else {
	    String msg = "Cannot find a game with id :" + bet.getGameId();
	    LOG.error(msg);
	    throw new RuntimeException(msg);
	}

	LOG.debug("Exit method validBet(Bet bet)");
    }
    
    public Bet getBet(String id) {
	LOG.debug("Entry method getBet(String id)");

	Long betId = Long.valueOf(id);
	Bet bet = repository.findById(betId).orElse(null);

	LOG.debug("bet=" + bet);
	LOG.debug("Exit method getBet(String id)");
	return bet;
    }

    public List<Bet> getAllBets() {
	LOG.debug("Entry method  getAllBets()");

	List<Bet> bets = repository.findAll();
	LOG.debug("Exit method  getAllBets()");
	return bets;
    }

    public List<Bet> getAllUserBets(String id) {
	LOG.debug("Entry method  getAllUserBets()");

	List<Bet> bets = repository.findAllUserBets(Long.valueOf(id));
	LOG.debug("Exit method  getAllUserBets()");
	return bets;
    }
}
