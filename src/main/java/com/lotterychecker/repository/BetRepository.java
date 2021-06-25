/**
 *
 */
package com.lotterychecker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lotterychecker.model.Bet;

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

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

    /**
     * @param id
     * @return
     */
    @Query("select b from Bet b where b.gameId = ?1")
    public List<Bet> findAllBetsForGame(Long gameId);

    @Query("select b from Bet b where b.userId = ?1")
    public List<Bet> findAllUserBets(Long id);

}
