
package com.jialu.minios.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AmazonS3Configuration {

	@Valid
	@NotNull
	@JsonProperty
	private String bucket;

	@Valid
	@NotNull
	@JsonProperty
	private String appId;

	@Valid
	@NotNull
	@JsonProperty
	private String appSecret;

	public String getBucket() {
		return bucket;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getAppId() {
		return appId;
	}

}
