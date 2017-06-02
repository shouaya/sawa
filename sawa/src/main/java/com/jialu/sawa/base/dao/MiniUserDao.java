package com.jialu.sawa.base.dao;

import org.hibernate.SessionFactory;

import com.jialu.sawa.base.model.MiniUserModel;
import com.jialu.sawa.utility.MiniDao;

public class MiniUserDao extends MiniDao<MiniUserModel> {

	public MiniUserDao(SessionFactory factory) {
		super(factory);
	}
}
