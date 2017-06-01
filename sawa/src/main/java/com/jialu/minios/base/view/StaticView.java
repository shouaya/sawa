package com.jialu.minios.base.view;

import java.util.HashMap;
import java.util.List;

import com.jialu.minios.utility.PropertiesUtil;
import com.jialu.minios.vo.MiniMeta;

import io.dropwizard.views.View;

public class StaticView extends View {
	
	private Boolean isDebug;
	private HashMap<String, String> page;
	private List<MiniMeta> properties;

	public StaticView(String viewName, String pageName) {
		super(viewName);
		mappingPageMessage(pageName);
		setIsDebug(false);
	}

	public HashMap<String, String> getPage() {
		return page;
	}
	
	private void mappingPageMessage(String pageName){
		page = PropertiesUtil.getMsgProperty(pageName);
		if(page == null){
			page = PropertiesUtil.getMsgProperty("unknown");
		}
	}

	public List<MiniMeta> getProperties() {
		return properties;
	}

	public void setProperties(List<MiniMeta> properties) {
		this.properties = properties;
	}

	public Boolean getIsDebug() {
		return isDebug;
	}

	public void setIsDebug(Boolean isDebug) {
		this.isDebug = isDebug;
	}
}
