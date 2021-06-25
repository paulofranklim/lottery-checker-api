/**
 *
 */
package com.lotterychecker.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.lotterychecker.vo.MailCredentialsVO;

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

public class CheckerUtil {
    private static final Logger LOG = LogManager.getLogger(CheckerUtil.class);

    public static String getHittedNumbers(String betNumbers, List<Integer> drawNumbers) {
	LOG.debug("Entry method getHittedNumbers(String betNumbers, List<String> drawNumbers)");

	List<Integer> betNumbersList = new ArrayList<Integer>();
	for (String number : Arrays.asList(betNumbers.split(","))) {
	    betNumbersList.add(Integer.valueOf(number));
	}

	TreeSet<Integer> orderedHittedNumbers = new TreeSet<Integer>();
	orderedHittedNumbers.addAll(betNumbersList.stream().distinct().filter(drawNumbers::contains).collect(Collectors.toSet()));
	
	LOG.debug("Exit method getHittedNumbers(String betNumbers, List<String> drawNumbers)");
	return orderedHittedNumbers.toString().replace(" ", "").replace("[", "").replace("]", "");
    }

    public static String getApiJSON(String url) {
	LOG.debug("Entry method getApiJSON(String url)");

	RestTemplate restTemplate = new RestTemplate();
	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
	HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

	LOG.debug("Exit method getApiJSON(String url)");
	return res.getBody();
    }
    
    public static MailCredentialsVO createErrorMailCredentials(String errorMsg, String mail) {
	LOG.debug("Entry method createErrorMailCredentials(String errorMsg, String mail)");
	
	StringBuilder message = new StringBuilder();
	message.append("Was ocurred an error in the application. \n");
	message.append("errorMsg= " + errorMsg);
	MailCredentialsVO mailCredentials = new MailCredentialsVO();
	mailCredentials.setSubject("ERROR: LotteryCheck");
	mailCredentials.setTo(mail);
	mailCredentials.setMessage(message);
	
	LOG.debug("Exit method createErrorMailCredentials(String errorMsg, String mail)");
	return mailCredentials;
    }

    public static String dateTimeFormatter(Instant instant) {
	LOG.debug("Entry method dateTimeFormatter(Instant instant)");

	DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG).withLocale(Locale.UK).withZone(ZoneId.systemDefault());
	String formattedData = formatter.format(instant);

	LOG.debug("formattedData=" + formattedData);
	LOG.debug("Exit method dateTimeFormatter(Instant instant)");
	return formattedData;
    }
}
