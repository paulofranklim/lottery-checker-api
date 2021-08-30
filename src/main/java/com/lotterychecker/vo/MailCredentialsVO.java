package com.lotterychecker.vo;

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

@Data
public class MailCredentialsVO {

    private String	  subject;
    private String	  to;
    private StringBuilder message;

}
