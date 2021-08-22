/**
 *
 */
package com.lotterychecker.components;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lotterychecker.service.CheckerService;
import com.lotterychecker.util.Constants;
import com.lotterychecker.util.Utils;

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

@Component
public class CheckerScheduled {
    private static final Logger	LOG = LogManager.getLogger(CheckerScheduled.class);

    @Autowired
    private CheckerService	checkService;
    
    @Value(Constants.GAMES_TO_CHECK_PROP)
    private String		games;

    @Scheduled(initialDelayString = Constants.INITIAL_DELAY_PROP, fixedDelayString = Constants.DELAY_PROP)
    private void scheduledCheck() {
	LOG.debug("Entry method scheduledCheck()");

	List<String> gameList = Arrays.asList(games.split(","));
	if (gameList != null && gameList.size() > 0) {

	    for (String game : gameList) {

		Instant start = Instant.now();
		LOG.info("Start check: " + game + " - " + Utils.dateTimeFormatter(start));

		checkService.checkResult(game, false);

		Instant end = Instant.now();
		LOG.info("End check - " + Utils.dateTimeFormatter(end));
		LOG.info("Duration: " + Duration.between(start, end).toMillis() + " millis.");
	    }
	    LOG.debug("Exit method scheduledCheck()");
	}
    }
}
