
package com.jialu.minios.configuration;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MiniConfiguration extends Configuration {

	@Valid
	@NotNull
	@JsonProperty
	private DataSourceFactory database = new DataSourceFactory();

	@Valid
	@NotNull
	@JsonProperty
	private String name;

	@Valid
	@NotNull
	@JsonProperty
	private String host;
	
	@Valid
	@NotNull
	@JsonProperty
	private String maxPost;

	@Valid
	@NotNull
	@JsonProperty
	private Boolean debug;
	
	@Valid
	@NotNull
	@JsonProperty
	private AmazonS3Configuration amazons3 = new AmazonS3Configuration();
	
	@Valid
	@NotNull
	@JsonProperty
	private SmsConfiguration sms = new SmsConfiguration();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDebug() {
		return debug;
	}

	public void setDebug(Boolean debug) {
		this.debug = debug;
	}

	public DataSourceFactory getDatabase() {
		return database;
	}

	public void setDatabase(DataSourceFactory database) {
		this.database = database;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public AmazonS3Configuration getAmazons3() {
		return amazons3;
	}

	public void setAmazons3(AmazonS3Configuration amazons3) {
		this.amazons3 = amazons3;
	}

	public SmsConfiguration getSms() {
		return sms;
	}

	public void setSms(SmsConfiguration sms) {
		this.sms = sms;
	}

	public String getMaxPost() {
		return maxPost;
	}

	public void setMaxPost(String maxPost) {
		this.maxPost = maxPost;
	}

}
