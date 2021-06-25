/**
 *
 */
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

import com.lotterychecker.model.Bet;
import com.lotterychecker.service.BetService;

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
public class BetController {

    private static final Logger	LOG = LogManager.getLogger(BetController.class);
    
    @Autowired
    private BetService		service;

    @RequestMapping(value = "save-bet", method = RequestMethod.POST)
    public String saveBet(@RequestBody Bet bet) {
	LOG.debug("Entry method saveBet(@RequestBody Bet bet)");
	Bet savedBet = service.saveBet(bet);
	LOG.debug(savedBet.toString());
	LOG.debug("Exit method saveBet(@RequestBody Bet bet)");
	return "Bet created. id=" + savedBet.getId();
    }
    
    @RequestMapping(value = "load-bet/{id}", method = RequestMethod.GET)
    public Bet loadBet(@PathVariable("id") String id) {
	LOG.debug("Entry method loadBet(@PathVariable(\"id\") String id) ");

	Bet bet = service.getBet(id);
	LOG.debug("Exit method loadBet(@PathVariable(\"id\") String id)");
	return bet;
    }
    
    @RequestMapping(value = "load-bets", method = RequestMethod.GET)
    public List<Bet> loadAllBets() {
	LOG.debug("Entry method loadAllBets()");

	List<Bet> bets = service.getAllBets();
	LOG.debug("Exit method loadAllBets()");
	return bets;
    }
    
    @RequestMapping(value = "load-bets/{id}", method = RequestMethod.GET)
    public List<Bet> loadUserBets(@PathVariable("id") String id) {
	LOG.debug("Entry method loadUserBets(@PathVariable(\"id\") String id)  ");

	List<Bet> bets = service.getAllUserBets(id);
	LOG.debug("Exit method loadUserBets(@PathVariable(\"id\") String id) ");
	return bets;
    }

}
