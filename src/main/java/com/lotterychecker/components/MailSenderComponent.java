package com.lotterychecker.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.lotterychecker.vo.MailCredentialsVO;

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
public class MailSenderComponent {
    private static final Logger	LOG = LogManager.getLogger(MailSenderComponent.class);

    @Autowired
    private JavaMailSender	mailSender;

    public void sendMail(MailCredentialsVO credentials) {
	LOG.debug("Entry method sendMail(MailCredentialsVO credentials)");

	var message = new SimpleMailMessage();
	message.setSubject(credentials.getSubject());
	message.setTo(credentials.getTo());
	message.setText(credentials.getMessage().toString());

	LOG.debug("credentials= " + credentials);
	try {
	    mailSender.send(message);
	}
	catch (MailException e) {
	    LOG.error("Error trying to send mail. Error= " + e.getMessage());
	}

	LOG.debug("Exit method sendMail(MailCredentialsVO credentials)");
    }
    
}
