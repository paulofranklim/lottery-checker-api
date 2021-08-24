package com.lotterychecker.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotterychecker.model.ApiResult;
import com.lotterychecker.process.ProcessResult;
import com.lotterychecker.process.SaveApiResult;
import com.lotterychecker.process.SendResult;
import com.lotterychecker.util.Constants;
import com.lotterychecker.util.Utils;
import com.lotterychecker.vo.ApiResultVO;

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

    @Autowired
    private SaveApiResult	saveApiResultService;

    @Autowired
    private ProcessResult	processResultService;

    @Autowired
    private SendResult		sendResultService;

    public boolean checkResult(String gameName, boolean forceCheck) {
	LOG.debug("Entry method checkResult(String gameName, boolean forceCheck)");

	boolean result = true;

	String URL = API_URL + gameName + TOKEN_PREFIX;
	LOG.debug("URL=" + URL);
	
	try {
	    
	    String apiJson = Utils.getApiJSON(URL);
	    ApiResultVO apiResultVO = new ObjectMapper().readValue(apiJson, ApiResultVO.class);

	    if (apiResultVO.getName() != null) {

		ApiResult savedApiResult = saveApiResultService.save(apiResultVO);

		if (savedApiResult != null) {
		    processResultService.process(savedApiResult, apiResultVO.getPrizes());
		    sendResultService.send();
		}

	    } else {
		throw new RuntimeException(apiJson);
	    }
	}
	catch (Exception e) {
	    String errorMsg = e.getMessage();
	    LOG.error("Error trying while create api object. " + errorMsg);
	    // TODO: Send mail with error message
	    result = false;
	}

	LOG.debug("Exit method checkResult(String gameName, boolean forceCheck)");
	return result;
    }

}
