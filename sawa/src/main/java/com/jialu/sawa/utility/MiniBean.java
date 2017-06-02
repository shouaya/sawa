
package com.jialu.sawa.utility;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jialu.sawa.configuration.MiniConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.dropwizard.hibernate.AbstractDAO;

public class MiniBean extends MiniConfiguration{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MiniBean.class);
		
	private HashMap<String, AbstractDAO<?>> daoList = new HashMap<String, AbstractDAO<?>>();
			
	@SuppressWarnings("unchecked")
	public <T> T getDao(Class<T> type) {
		AbstractDAO<?> baseDao = daoList.get(type.getName());
		return (T) baseDao;
	}
	
	public HashMap<String, AbstractDAO<?>> getDaoList() {
		return daoList;
	}

	public void setDaoList(HashMap<String, AbstractDAO<?>> daoList) {
		this.daoList = daoList;
	}
	
	public String sendSms(String to, String content){
		try {
			PhoneNumber toN = new PhoneNumber(to);
			PhoneNumber fromN = new PhoneNumber(super.getSms().getFrom());
			Message message = Message.creator(toN, fromN, content).create();
			LOGGER.info("SmsProcess send to:" + to + " body:" + content);
			return message.getSid();
		}catch(Exception ex){
			LOGGER.error("sendSms ", ex);
		}
		return null;
	}
	

}
