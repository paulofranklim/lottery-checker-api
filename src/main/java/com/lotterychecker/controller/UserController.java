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

import com.lotterychecker.model.User;
import com.lotterychecker.service.UserService;

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
public class UserController {

    private static final Logger	LOG = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService		service;

    @RequestMapping(value = "save-user", method = RequestMethod.POST)
    public String saveUser(@RequestBody User user) {
	LOG.debug("Entry method saveUser(@RequestBody User user)");
	User savedUser = service.saveUser(user);
	LOG.debug(savedUser.toString());
	LOG.debug("Exit method saveUser(@RequestBody User user)");
	return "User '" + savedUser.getName() + "' created. id=" + savedUser.getId();
    }

    @RequestMapping(value = "load-user/{id}", method = RequestMethod.GET)
    public User loadUser(@PathVariable("id") String id) {
	LOG.debug("Entry method loadUser(@PathVariable(\"id\") String id) ");

	User user = service.getUser(id);
	LOG.debug("Exit method loadUser(@PathVariable(\"id\") String id)");
	return user;
    }
    
    @RequestMapping(value = "load-users", method = RequestMethod.GET)
    public List<User> loadAllUsers() {
	LOG.debug("Entry method loadAllUsers()");

	List<User> users = service.getAllUsers();
	LOG.debug("Exit method loadAllUsers()");
	return users;
    }

}
