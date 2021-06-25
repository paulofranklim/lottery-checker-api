/**
 *
 */
package com.lotterychecker.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotterychecker.model.User;
import com.lotterychecker.service.AuthenticationService;

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

@RestController
public class AuthenticationController {
    private static final Logger	  LOG = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService service;
    
    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public User auth(@RequestBody User authUser) {
	LOG.debug("Entry method auth(@RequestBody User authUser)");
	
	String userName = authUser.getUserName();
	String password = authUser.getPassword();
	
	User user = service.auth(userName, password);

	LOG.debug("Exit method auth(@RequestBody User authUser)");
	return user;
    }
}
