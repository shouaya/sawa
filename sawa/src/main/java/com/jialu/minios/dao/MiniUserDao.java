package com.jialu.minios.dao;

import org.hibernate.SessionFactory;

import com.jialu.minios.model.MiniUserModel;
import com.jialu.minios.utility.MiniDao;

public class MiniUserDao extends MiniDao<MiniUserModel> {

	public MiniUserDao(SessionFactory factory) {
		super(factory);
	}
}
