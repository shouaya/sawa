package com.jialu.sawa.base.process;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.jialu.sawa.base.dao.MiniUserDao;
import com.jialu.sawa.base.model.MiniUserModel;
import com.jialu.sawa.utility.MiniBean;
import com.jialu.sawa.utility.OpResult;
import com.jialu.sawa.vo.MiniPair;
import com.jialu.sawa.vo.MiniQuery;
import com.jialu.sawa.vo.OperatorResult;

public class UserProcess {
	
	public static OperatorResult<MiniUserModel> sendResgistCode(String phone, MiniBean config) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		OperatorResult<MiniUserModel> or = new OperatorResult<MiniUserModel>();
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
		if(config.getDebug()){
			code = "0000";
		}
		or.setCode(OpResult.OK.name());
		MiniUserModel userM = perRegist(code, phone, config);
		or.setData(userM);
		if(config.getDebug()){
			return or;
		}
		String mid = config.sendSms(to, String.format("%s code： %s", config.getName(), code));
		if(mid == null){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("send message error");
		}
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
	
	private static MiniUserModel perRegist(String code, String phone, MiniBean config) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{	
		MiniUserDao dao = config.getDao(MiniUserDao.class);
		MiniUserModel user = getUserByPhone(dao, phone);
		if(user == null){
			user = new MiniUserModel();
			user.setPhone(phone);
			user.setRole(config.getDefaultUserRole());
		}
		user.setPass(code);
		dao.save(user);
		return user;
	}

	public static OperatorResult<MiniUserModel> regist(String phone, String code, MiniBean config) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		OperatorResult<MiniUserModel> or = new OperatorResult<MiniUserModel>();
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
		or.setData(user);
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
