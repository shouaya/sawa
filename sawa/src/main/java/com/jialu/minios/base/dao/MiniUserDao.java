package com.jialu.minios.base.dao;

import org.hibernate.SessionFactory;

import com.jialu.minios.base.model.MiniUserModel;
import com.jialu.minios.utility.MiniDao;

public class MiniUserDao extends MiniDao<MiniUserModel> {

	public MiniUserDao(SessionFactory factory) {
		super(factory);
	}
}
