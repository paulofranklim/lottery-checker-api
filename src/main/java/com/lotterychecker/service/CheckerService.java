package com.lotterychecker.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotterychecker.components.MailSenderComponent;
import com.lotterychecker.model.ApiResult;
import com.lotterychecker.model.CheckedResult;
import com.lotterychecker.process.ProcessResult;
import com.lotterychecker.process.SaveApiResult;
import com.lotterychecker.process.SendResult;
import com.lotterychecker.util.Constants;
import com.lotterychecker.util.Utils;
import com.lotterychecker.vo.ApiResultVO;
import com.lotterychecker.vo.MailCredentialsVO;

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
public class CheckerService {
    private static final Logger	LOG = LogManager.getLogger(CheckerService.class);

    @Value(Constants.API_URL_PROP)
    private String		API_URL;

    @Value(Constants.TOKEN_PROP)
    private String		TOKEN_PREFIX;
    
    @Value(Constants.MAIL_ERROR_PROP)
    private String		ERROR_MAIL;
    
    @Autowired
    private SaveApiResult	saveApiResultService;
    
    @Autowired
    private ProcessResult	processResultService;
    
    @Autowired
    private SendResult		sendResultService;
    
    @Autowired
    private MailSenderComponent	mailSender;
    
    public boolean checkResult(String gameName, boolean forceCheck) {
	LOG.debug("Entry method checkResult(String gameName, boolean forceCheck)");
	
	boolean result = true;
	
	String URL = API_URL + gameName + TOKEN_PREFIX;
	LOG.debug("URL=" + URL);

	try {

	    String apiJson = Utils.getApiJSON(URL);
	    LOG.debug("apiJson= " + apiJson);
	    ApiResultVO apiResultVO = new ObjectMapper().readValue(apiJson, ApiResultVO.class);
	    
	    if (apiResultVO.getName() != null) {
		
		ApiResult savedApiResult = saveApiResultService.save(apiResultVO);
		
		if (savedApiResult != null) {
		    List<CheckedResult> checkedResults = processResultService.process(savedApiResult, apiResultVO.getPrizes());
		    sendResultService.send(checkedResults, gameName);
		}
		
	    } else {
		throw new RuntimeException(apiJson);
	    }
	}
	catch (Exception e) {
	    String errorMsg = e.getMessage();
	    LOG.error("Error trying to process the results. " + errorMsg);

	    MailCredentialsVO credentials = new MailCredentialsVO();
	    credentials.setTo(ERROR_MAIL);
	    credentials.setSubject(Constants.ERROR_MAIL_SUBJECT);
	    credentials.setMessage(new StringBuilder(Constants.ERROR_MAIL_MESSAGE + e.getMessage()));

	    mailSender.sendMail(credentials);

	    result = false;
	}
	
	LOG.debug("Exit method checkResult(String gameName, boolean forceCheck)");
	return result;
    }
    
}
