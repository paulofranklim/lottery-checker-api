/**
 *
 */
package com.lotterychecker.process;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lotterychecker.model.ApiResult;
import com.lotterychecker.model.Bet;
import com.lotterychecker.model.CheckedResult;
import com.lotterychecker.model.Game;
import com.lotterychecker.model.User;
import com.lotterychecker.repository.BetRepository;
import com.lotterychecker.repository.CheckedResultRepository;
import com.lotterychecker.repository.GameRepository;
import com.lotterychecker.repository.UserRepository;
import com.lotterychecker.util.Utils;
import com.lotterychecker.vo.ApiPrizeVO;

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
public class ProcessResult {
    private static final Logger	    LOG	= LogManager.getLogger(ProcessResult.class);
    
    @Autowired
    private BetRepository	    betRepository;

    @Autowired
    private UserRepository	    userRepository;
    
    @Autowired
    private GameRepository	    gameRepository;
    
    @Autowired
    private CheckedResultRepository checkedResultRepository;
    
    public List<CheckedResult> process(ApiResult apiResult, List<ApiPrizeVO> prizeList) {
	LOG.debug("Entry method process(ApiResult apiResult, List<ApiPrizeVO> prizeList)");

	LOG.debug("Selecting all bets for game id = " + apiResult.getGameId());
	List<Bet> bets = betRepository.findAllBetsForGameOrderedByUserId(apiResult.getGameId());
	
	List<CheckedResult> results = new ArrayList<CheckedResult>();

	if (!bets.isEmpty()) {
	    LOG.debug("Selected " + bets.size() + " bets.");

	    HashMap<Long, BigDecimal> prizes = new HashMap<Long, BigDecimal>();
	    prizeList.forEach(p -> prizes.put(Long.valueOf(p.getHits()), new BigDecimal(p.getTotalValue())));
	    LOG.debug("Prizes= " + prizeList);

	    LOG.debug("Cheking results");
	    bets.forEach(bet -> {
		User user = userRepository.findById(bet.getUserId()).orElse(null);

		CheckedResult checkedResult = new CheckedResult();
		checkedResult.setUserId(user.getId());
		checkedResult.setUserMail(user.getMail());
		checkedResult.setBetId(bet.getId());
		checkedResult.setBetNumbers(bet.getNumbers());
		checkedResult.setGameId(bet.getGameId());
		checkedResult.setDrawnDate(apiResult.getDrawnDate());
		checkedResult.setDrawnNumber(apiResult.getDrawnNumber());
		checkedResult.setDrawnNumbers(apiResult.getDrawnNumbers());
		checkedResult.setHittedNumbers(Utils.getHittedNumbers(bet.getNumbers(), apiResult.getDrawnNumbers()));
		checkedResult.setHits(checkedResult.getHittedNumbers().split(",").length);
		checkedResult.setPrize(prizes.get(Long.valueOf(checkedResult.getHits())));

		bet.setLastCheck(LocalDate.now());
		results.add(checkedResult);

		LOG.debug("checkedResult= " + checkedResult);

	    });
	    
	    LOG.debug("Saving all checked results.");
	    checkedResultRepository.saveAll(results);

	    LOG.debug("Saving all bets with actualized fiel 'lastcheck' ");
	    betRepository.saveAll(bets);
	}
	
	Game game = gameRepository.findById(apiResult.getGameId()).orElse(null);
	game.setLastDrawn(apiResult.getDrawnNumber());

	LOG.debug("Saving game with actualized field 'lastdrawn'");
	gameRepository.save(game);

	LOG.debug("Exit method process(ApiResult apiResult, List<ApiPrizeVO> prizeList)");
	return results;
    }
    
}
