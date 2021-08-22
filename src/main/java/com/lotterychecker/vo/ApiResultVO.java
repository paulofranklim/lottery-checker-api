package com.lotterychecker.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

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

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiResultVO {

    @JsonProperty("numero_concurso")
    private Long	     drawnNumber;
    @JsonProperty("data_concurso")
    private String	     date;
    @JsonProperty("dezenas")
    private List<Integer>    numbers;
    @JsonProperty("nome")
    private String	     name;
    @JsonProperty("premiacao")
    private List<ApiPrizeVO> prizes;
    
}
