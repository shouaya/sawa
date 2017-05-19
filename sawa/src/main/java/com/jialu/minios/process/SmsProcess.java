package com.jialu.minios.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsProcess {
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsProcess.class);

	public static String sendSms(String to, String from, String body) {
		PhoneNumber toN = new PhoneNumber(to);
		PhoneNumber fromN = new PhoneNumber(from);
		Message message = Message.creator(toN, fromN, body).create();
		LOGGER.info("SmsProcess send to:" + to + " body:" + body);
		return message.getSid();
	}
}
