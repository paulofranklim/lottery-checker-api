package com.lotterychecker.model;

import java.math.BigDecimal;

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
 * Copyright 2020 github.com/paulofranklim
 * </pre>
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CheckedResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long       id;
    private Long       gameId;
    private Long       betId;
    private Long       userId;
    private String     userMail;
    private Long       drawnNumber;
    private String     drawnDate;
    private String     drawnNumbers;
    private String     betNumbers;
    private String     hittedNumbers;
    private int	       hits;
    private BigDecimal prize;
    private boolean    sent;

}
