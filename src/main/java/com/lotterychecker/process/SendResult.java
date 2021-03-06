package com.lotterychecker.process;

import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotterychecker.components.MailSenderComponent;
import com.lotterychecker.model.CheckedResult;
import com.lotterychecker.util.Constants;
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

@Service
public class SendResult {
    private static final Logger	LOG = LogManager.getLogger(SendResult.class);

    @Autowired
    private MailSenderComponent	sender;
    
    public void send(List<CheckedResult> checkedResults, String gameName) {
	LOG.debug("Entry method send(List<CheckedResult> checkedResults, String gameName)");

	CheckedResult result = null;
	Long lastUserId = Long.valueOf(0);

	MailCredentialsVO credentials = new MailCredentialsVO();
	StringBuilder message = new StringBuilder();

	for (Iterator<CheckedResult> i = checkedResults.iterator(); i.hasNext();) {

	    result = i.next();
	    LOG.debug("lastUserId= " + lastUserId);

	    if (lastUserId.longValue() > 0 && result.getUserId().compareTo(lastUserId) != 0) {
		LOG.debug("Sending e-mail");
		sender.sendMail(credentials);
		message = new StringBuilder();
		credentials = new MailCredentialsVO();
	    }
	    
	    message.append(appendMessageInfo(result));
	    credentials.setMessage(message);
	    credentials.setSubject(Constants.MAIL_SUBJECT + gameName + " - " + result.getDrawnNumber());
	    credentials.setTo(result.getUserMail());
	    
	    if (!i.hasNext()) {
		LOG.debug("Sending e-mail");
		sender.sendMail(credentials);
	    }

	    lastUserId = result.getUserId();

	}
	LOG.debug("Exit method send(List<CheckedResult> checkedResults, String gameName)");
    }
    
    private String appendMessageInfo(CheckedResult result) {
	LOG.debug("Entry method appendMessageInfo(CheckedResult result)");
	
	StringBuilder message = new StringBuilder();
	message.append(Constants.YOUR_BET_NUMBERS + result.getBetNumbers() + Constants.LINE);
	message.append(Constants.DRAWN_NUMBERS + result.getDrawnNumbers() + Constants.LINE);
	message.append(Constants.YOUR_HITS + result.getHittedNumbers() + Constants.LINE);
	message.append(Constants.QTY_HITS + result.getHits() + Constants.LINE);
	message.append(Constants.YOUR_PRIZE + result.getPrize() + Constants.LINE);
	message.append(Constants.LINE);

	LOG.debug("Ecit method appendMessageInfo(CheckedResult result)");
	return message.toString();
    }
}
