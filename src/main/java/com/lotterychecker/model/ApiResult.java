/**
 *
 */
package com.lotterychecker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ApiResult {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long    id;
    private Long    gameId;
    private Long    drawnNumber;
    private String  drawnDate;
    private String  drawnNumbers;
    private boolean accumulated;
    private String  accumulatedValue;
    private String  prizes;
    private String  nextDrawnDate;
    private String  nextDrawPrize;
}
