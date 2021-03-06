package com.lotterychecker.process;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotterychecker.model.ApiResult;
import com.lotterychecker.model.Game;
import com.lotterychecker.repository.ApiResultRepository;
import com.lotterychecker.repository.GameRepository;
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
 * Copyright 2021 github.com/paulofranklim
 * </pre>
 */

@Service
public class SaveApiResult {
    public static final Logger	LOG = LogManager.getLogger(SaveApiResult.class);

    @Autowired
    private ApiResultRepository	apiResultRepository;
    
    @Autowired
    private GameRepository	gameReposity;

    public ApiResult save(ApiResultVO vo) {
	LOG.debug("Entry method save(ApiResultVO vo)");

	ApiResult result = null;

	Game game = gameReposity.findGameByName(vo.getName());

	if (game != null && game.getLastDrawn().compareTo(vo.getDrawnNumber()) < 0) {
	    LOG.debug("game= " + game);

	    result = new ApiResult();

	    result.setGameId(game.getId());
	    result.setDrawnNumber(vo.getDrawnNumber());
	    result.setDrawnDate(vo.getDate());
	    result.setAccumulated(vo.isAccumulated());
	    result.setAccumulatedValue(vo.getAccumulatedPrize());
	    result.setNextDrawnDate(vo.getNextDrawnDate());
	    result.setNextDrawPrize(vo.getNextDrawnPrize());
	    result.setPrizes(Utils.listSeparetedWithComaToString(vo.getPrizes()));
	    result.setDrawnNumbers(Utils.listSeparetedWithComaToString(vo.getNumbers()));

	    result = apiResultRepository.save(result);

	    LOG.debug("API Result saved");
	}

	LOG.debug("result=" + result);
	LOG.debug("Exit method save(ApiResultVO vo)");
	return result;
    }
}
