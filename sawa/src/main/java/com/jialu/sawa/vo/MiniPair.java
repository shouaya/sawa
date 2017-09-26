package com.jialu.sawa.vo;

public class MiniPair {
	
	public MiniPair(){
		
	}
	public MiniPair(String key, String value){
		this.key = key;
		this.value = value;
	}

	private String key;
	private String value;
	private Class<?> clazz;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
