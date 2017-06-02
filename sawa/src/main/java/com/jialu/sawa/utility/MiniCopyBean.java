package com.jialu.sawa.utility;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

public class MiniCopyBean extends BeanUtilsBean {
	@Override
	public void copyProperty(Object dest, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {
		if (value == null) {
			return;
		}
		super.copyProperty(dest, name, value);
	}
}
