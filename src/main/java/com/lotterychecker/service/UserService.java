/**
 *
 */
package com.lotterychecker.service;

import java.util.List;

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
public class UserService {
    private static final Logger	LOG = LogManager.getLogger(UserService.class);
    
    @Autowired
    private UserRepository	repository;

    public User saveUser(User user) {
	LOG.debug("Entry method saveUser(User user)");

	validUser(user);
	User savedUser = null;
	try {
	    savedUser = repository.save(user);
	}
	catch (Exception e) {
	    String msg = e.getMessage();
	    LOG.error("Error while trying save user. " + msg);
	    throw new RuntimeException(msg);
	}

	LOG.debug("Exit method saveUser(User user)");
	return savedUser;
    }

    private void validUser(User user) {
	LOG.debug("Entry method validUser(User user)");

	if (user.getName() == null || user.getMail() == null || user.getPassword() == null) {
	    String msg = "The fields: name, mail and password are required.";
	    LOG.error(msg);
	    throw new RuntimeException(msg);
	}

	LOG.debug("Exit method validUser(User user)");
    }

    public User getUser(String id) {
	LOG.debug("Entry method getUser(String id)");
	
	Long userId = Long.valueOf(id);
	User user = repository.findById(userId).orElse(null);

	LOG.debug("user=" + user);
	LOG.debug("Exit method getUser(String id)");
	return user;
    }
    
    public List<User> getAllUsers() {
	LOG.debug("Entry method  getAllUsers()");

	List<User> users = repository.findAll();
	LOG.debug("Exit method  getAllUsers()");
	return users;
    }
}
