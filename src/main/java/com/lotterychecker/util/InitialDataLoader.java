/**
 *
 */
package com.lotterychecker.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.lotterychecker.model.Bet;
import com.lotterychecker.model.Game;
import com.lotterychecker.model.User;
import com.lotterychecker.repository.BetRepository;
import com.lotterychecker.repository.GameRepository;
import com.lotterychecker.repository.UserRepository;

/**
 * <pre>
 * Author         : Paulo Franklim, paulofranklim@hotmail.com
 * Purpose        : <Purpose>
 * Input files    : N/A
 * Log File       : N/A
 * Output file    : N/A
 *
 * Copyright 2021 github.com/paulofranklim
 * </pre>
 */
@Component
public class InitialDataLoader implements ApplicationRunner {
    private static final Logger	LOG = LogManager.getLogger(InitialDataLoader.class);

    @Autowired
    private UserRepository	userRepository;

    @Autowired
    private GameRepository	gameRepository;
    
    @Autowired
    private BetRepository	betRepository;

    @Value(CheckerConstants.DDL_AUTO_PROP)
    private String		ddlAutoProp;
    
    @Override
    public void run(ApplicationArguments args) {
	if ("create".equalsIgnoreCase(ddlAutoProp)) {
	    LOG.info("Starting initial values");
	    
	    User admin = userRepository.save(new User(null, "Admin", "admin", "admin", "admin", true));
	    User user = userRepository.save(new User(null, "Paulo Franklim", "paulofranklim@hotmail.com", "pfranklim", "1234", true));

	    Game lotofacil = gameRepository.save(new Game(null, "lotofacil", 15, 20, 25, (long) 0, true));
	    Game megasena = gameRepository.save(new Game(null, "megasena", 6, 15, 60, (long) 0, true));
	    Game quina = gameRepository.save(new Game(null, "quina", 5, 15, 80, (long) 0, true));
	    
	    betRepository.save(
	            new Bet(null, true, BigDecimal.ZERO, "1,3,5,6,8,9,10,13,14,16,18,20,21,22,24", user.getId(), lotofacil.getId(), LocalDate.of(2021, 1, 1)));
	    betRepository.save(
	            new Bet(null, true, BigDecimal.ZERO, "1,2,3,9,14,18,20,27,36,37,38,40,44,46,55", user.getId(), megasena.getId(), LocalDate.of(2021, 1, 1)));
	    betRepository.save(
	            new Bet(null, true, BigDecimal.ZERO, "1,3,5,6,8,9,10,13,14,16,18,20,21,22,24", admin.getId(), lotofacil.getId(), LocalDate.of(2021, 1, 1)));
	    
	    LOG.info("Finishing initial values");
	}
    }
}
