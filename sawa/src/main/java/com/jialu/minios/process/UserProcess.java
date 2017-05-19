package com.jialu.minios.process;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jialu.minios.dao.MiniUserDao;
import com.jialu.minios.model.MiniUserModel;
import com.jialu.minios.utility.MiniBean;
import com.jialu.minios.utility.MiniConstants;
import com.jialu.minios.utility.OpResult;
import com.jialu.minios.vo.MiniPair;
import com.jialu.minios.vo.MiniQuery;
import com.jialu.minios.vo.MiniUser;
import com.jialu.minios.vo.OperatorResult;

public class UserProcess {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProcess.class);

	public static OperatorResult<MiniUser> sendResgistCode(String phone, MiniBean config) throws IllegalAccessException, InvocationTargetException {
		OperatorResult<MiniUser> or = new OperatorResult<MiniUser>();
		if(phone == null){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("phone number is empty");
			return or;
		}
		if(!StringUtils.isNumeric(phone)){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("phone number is not real");
			return or;
		}
		if(phone.length() != 11){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("phone number is not real");
			return or;
		}
		String to = "";
		if(phone.substring(0, 1).equals("1")){
			to = "+86" + phone;
		}else if(phone.substring(0, 1).equals("0")){
			to = "+81" + phone;
		}
		String code = UtilProcess.random4Code();
		try {
			SmsProcess.sendSms(to, config.getSms().getFrom(), "酒家路注册码：" + code);
		}catch(Exception ex){
			LOGGER.error("sendResgistCode ", ex);
			or.setCode(OpResult.ERROR.name());
			or.setMsg("send message error");
			return or;
		}
		or.setCode(OpResult.OK.name());
		MiniUserModel userM = perRegist(code, phone, config);
		MiniUser userVO = new MiniUser();
		userVO.setPhone(userM.getPhone());
		userVO.setId(userM.getId());
		or.setData(userVO);
		return or;
	}
	
	private static MiniUserModel getUserByPhone(MiniUserDao dao, String phone){
		MiniQuery query = new MiniQuery();
		query.setName("find_user_by_phone");
		List<MiniPair> params = new ArrayList<MiniPair>();
		params.add(new MiniPair("phone", phone));
		query.setParams(params);
		return dao.findOneByQuery(query);
	}
	
	private static MiniUserModel perRegist(String code, String phone, MiniBean config) throws IllegalAccessException, InvocationTargetException{	
		MiniUserDao dao = config.getDao(MiniUserDao.class);
		MiniUserModel user = getUserByPhone(dao, phone);
		if(user == null){
			user = new MiniUserModel();
			user.setPhone(phone);
			user.setRole(MiniConstants.ROLE_USER);
		}
		user.setPass(code);
		dao.save(user);
		return user;
	}

	public static OperatorResult<MiniUser> regist(String phone, String code, MiniBean config) throws IllegalAccessException, InvocationTargetException {
		OperatorResult<MiniUser> or = new OperatorResult<MiniUser>();
		if(phone == null || code == null){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("phone or code is empty");
			return or;
		}
		MiniUserDao dao = config.getDao(MiniUserDao.class);
		MiniUserModel user = getUserByPhone(dao, phone);
		if(user == null){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("phone not exsit");
			return or;
		}
		if(!user.getPass().equals(code)){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("code not exsit");
			return or;
		}
		user.setToken(DigestUtils.sha1Hex(phone + config.getName() + System.currentTimeMillis()));
		dao.save(user);
		or.setCode(OpResult.OK.name());
		MiniUser userVO = new MiniUser();
		userVO.setId(user.getId());
		userVO.setPhone(user.getPhone());
		userVO.setToken(user.getToken());
		userVO.setPass(user.getPass());
		userVO.setName(user.getName());
		userVO.setTitle(user.getTitle());
		userVO.setAvatar(user.getAvatar());
		or.setData(userVO);
		return or;
	}
	
	public static MiniUserModel findByIdAndToken(MiniUserDao dao, String uid, String token){
		MiniQuery query = new MiniQuery();
		query.setName("find_user_by_phone_and_token");
		List<MiniPair> params = new ArrayList<MiniPair>();
		params.add(new MiniPair("phone", uid));
		params.add(new MiniPair("token", token));
		query.setParams(params);
		return dao.findOneByQuery(query);
	}

}
