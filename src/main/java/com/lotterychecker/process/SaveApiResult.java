package com.lotterychecker.process;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotterychecker.model.ApiResult;
import com.lotterychecker.model.Game;
import com.lotterychecker.repository.ApiResultRepository;
import com.lotterychecker.repository.GameRepository;
import com.lotterychecker.vo.ApiResultVO;

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
public class SaveApiResult {
    public static final Logger LOG = LogManager.getLogger(SaveApiResult.class);
    
    @Autowired
    ApiResultRepository	       apiResultRepository;

    @Autowired
    GameRepository	       gameReposity;
    
    public ApiResult saveApiResult(ApiResultVO vo) {
	LOG.debug("Entry method saveApiResult(ApiResultVO vo)");
	
	ApiResult result = null;
	
	Game game = gameReposity.findGameByName(vo.getName());
	
	if (game != null && game.getLastDrawn().compareTo(vo.getDrawnNumber()) < 0) {
	    LOG.debug("game=" + game);
	    
	    result = new ApiResult();
	    
	    result.setGameName(vo.getName());
	    result.setDrawnNumber(vo.getDrawnNumber());
	    result.setDrawnDate(vo.getDate());
	    // Saving the array in a string without '[' and ']'
	    // characters
	    result.setPrizes(vo.getPrizes().stream().map(s -> s.toString()).collect(Collectors.joining(",")));
	    result.setAccumulated(false);
	    result.setAccumulatedValue(null);
	    result.setNextDrawnDate(null);
	    result.setNextDrawPrize(null);
	    
	    result = apiResultRepository.save(result);
	    
	    LOG.debug("API Result saved");
	}
	
	LOG.debug("result=" + result);
	LOG.debug("Exit method saveApiResult(ApiResultVO vo)");
	return result;
    }
}
