/**
 *
 */
package com.lotterychecker.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotterychecker.model.User;
import com.lotterychecker.repository.UserRepository;

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
public class AuthenticationService {
    private static final Logger	LOG = LogManager.getLogger(AuthenticationService.class);
    
    @Autowired
    private UserRepository	repository;

    public User auth(String mail, String password) {
	LOG.debug("Entry method auth(String mail, String password)");
	LOG.debug("mail=" + mail);

	User result = repository.getAuthenticatedUser(mail, password);
	if (result != null) {
	    LOG.debug("Cleaning password");
	    result.setPassword(null);
	}
	if (!result.isActive()) {
	    throw new RuntimeException("The user is not active.");
	}

	LOG.debug("Entry method auth(String mail, String password)");
	return result;
    }

}
