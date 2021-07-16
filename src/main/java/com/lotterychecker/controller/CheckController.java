/**
 *
 */
package com.lotterychecker.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotterychecker.service.CheckerService;

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
public class CheckController {

    private static final Logger	LOG = LogManager.getLogger(CheckController.class);

    @Autowired
    CheckerService		service;
    
    @RequestMapping(value = "/check-result/{game}", method = RequestMethod.GET)
    public boolean checkResult(@PathVariable("game") String game) {
	LOG.debug("Entry method checkResult(@PathVariable(\"game\") String game)");
	boolean result = service.checkResult(game, false);
	LOG.debug("Exit method checkResult(@PathVariable(\"game\") String game)");
	return result;
    }

    @RequestMapping(value = "/force-check-result/{game}", method = RequestMethod.GET)
    public boolean forceCheckResult(@PathVariable("game") String game) {
	LOG.debug("Entry method forceCheckResult(@PathVariable(\"game\") String game)");
	boolean result = service.checkResult(game, true);
	LOG.debug("Exit method forceCheckResult(@PathVariable(\"game\") String game)");
	return result;
    }
    
}
