/**
 *
 */
package com.lotterychecker.util;

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

public class Constants {

    public static final String DDL_AUTO_PROP	   = "${spring.jpa.hibernate.ddl-auto}";
    public static final String DELAY_PROP	   = "${application.prop.api.scheduler.delay:3600000}";
    public static final String INITIAL_DELAY_PROP  = "${application.prop.api.scheduler.initial.delay:0}";
    public static final String GAMES_TO_CHECK_PROP = "${application.prop.api.scheduler.game.types}";
    public static final String API_URL_PROP	   = "${application.prop.api.url}";
    public static final String TOKEN_PROP	   = "${application.prop.api.token}";
    public static final String MAIL_RESULT_PROP	   = "${application.prop.api.result.mail}";
    public static final String MAIL_ERROR_PROP	   = "${application.prop.api.error.mail}";
    
    public static final String LINE		   = "\n";

    public static final String MAIL_SUBJECT	   = "Lottery Checker - Drawn ";
    public static final String ERROR_MAIL_SUBJECT  = "ERROR LOTTERY CHECKER";
    public static final String ERROR_MAIL_MESSAGE  = "Error on Lottery Checker Application. ";
    
    public static final String DRAW_NUMBER_PREFIX  = "&concurso=";
    
}
