
package com.jialu.minios.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SmsConfiguration {

	@Valid
	@NotNull
	@JsonProperty
	private String name;
	
	@Valid
	@NotNull
	@JsonProperty
	private String key;
	
	@Valid
	@NotNull
	@JsonProperty
	private String sid;
	
	@Valid
	@NotNull
	@JsonProperty
	private String token;
	
	@Valid
	@NotNull
	@JsonProperty
	private String from;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
