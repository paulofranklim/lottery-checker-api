package com.lotterychecker.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

public class Utils {
    private static final Logger LOG = LogManager.getLogger(Utils.class);

    public static String getHittedNumbers(String betNumbers, String drawnNumbers) {
	LOG.debug("Entry method getHittedNumbers(String betNumbers, String drawNumbers)");

	List<Integer> betNumbersList = new ArrayList<Integer>();
	for (String number : betNumbers.split(",")) {
	    betNumbersList.add(Integer.valueOf(number));
	}

	List<Integer> drawnNumbersList = new ArrayList<Integer>();
	for (String number : drawnNumbers.split(",")) {
	    drawnNumbersList.add(Integer.valueOf(number));
	}
	TreeSet<Integer> orderedHittedNumbers = new TreeSet<Integer>();
	orderedHittedNumbers.addAll(betNumbersList.stream().distinct().filter(drawnNumbersList::contains).collect(Collectors.toSet()));

	LOG.debug("Exit method getHittedNumbers(String betNumbers, String drawNumbers)");
	return listSeparetedWithComaToString(orderedHittedNumbers);
    }

    public static String getApiJSON(String url) {
	LOG.debug("Entry method getApiJSON(String url)");
	
	var result = "";
	var request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Constants.API_TIMEOUT).build();
	var client = HttpClient.newBuilder().connectTimeout(Constants.API_TIMEOUT).build();

	try {
	    var response = client.send(request, BodyHandlers.ofString());
	    LOG.debug("response= " + response);
	    result = response.body();
	}
	catch (IOException | InterruptedException e) {
	    result = e.getMessage();
	    LOG.error("Error to call API. Error= " + result);
	}

	LOG.debug("Exit method getApiJSON(String url)");
	return result;
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String listSeparetedWithComaToString(Collection list) {
	LOG.debug("Entry method listSeparetedWithComaToString(Collection list)");
	String result = "";
	
	if (!list.isEmpty()) {
	    // Saving the array in a string without '[' and ']'
	    // characters
	    Stream stream = list.stream();
	    result = (String) stream.map(e -> e.toString()).collect(Collectors.joining(","));
	    LOG.debug("entry= " + list.toString());
	    LOG.debug("exit= " + result);
	}

	LOG.debug("Exit method listSeparetedWithComaToString(Collection list)");
	return result;
	
    }
}
