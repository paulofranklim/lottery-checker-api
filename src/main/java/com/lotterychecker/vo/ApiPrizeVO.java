/**
 *
 */
package com.lotterychecker.vo;

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
 * Copyright 2021 github.com/paulofranklim
 * </pre>
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ApiPrizeVO {
    @JsonProperty("valor_total")
    private String totalValue;
    
    @JsonProperty("acertos")
    private String hits;
    
}
