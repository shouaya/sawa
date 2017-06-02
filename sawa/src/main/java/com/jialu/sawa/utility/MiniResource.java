package com.jialu.sawa.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MiniResource {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(MiniResource.class);
	
	protected MiniBean config;
	
	public MiniResource(MiniBean config){
		this.config = config;
	}
	
	public MiniBean getConfigBean(){
		return this.config;
	}
}
