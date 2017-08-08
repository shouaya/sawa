
package com.jialu.sawa.configuration;

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
	private String cuscss;
	
	@Valid
	@NotNull
	@JsonProperty
	private String cusjs;

	@Valid
	@NotNull
	@JsonProperty
	private Boolean secure;
	
	@Valid
	@NotNull
	@JsonProperty
	private String maxPost;
	
	@Valid
	@NotNull
	@JsonProperty
	private String search;
	
	@Valid
	@NotNull
	@JsonProperty
	private String defaultUserRole;
	
	@Valid
	@NotNull
	@JsonProperty
	private String defaultAdminRole;
	
	@Valid
	@NotNull
	@JsonProperty
	private String packageDao;
	
	@Valid
	@NotNull
	@JsonProperty
	private String packageModel;
	
	@Valid
	@NotNull
	@JsonProperty
	private String packageResource;
	
	@Valid
	@NotNull
	@JsonProperty
	private String packageApi;
		
	@Valid
	@NotNull
	@JsonProperty
	private Boolean debug;
	
	@Valid
	@NotNull
	@JsonProperty
	private Boolean chatable;
	
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

	public String getDefaultUserRole() {
		return defaultUserRole;
	}

	public void setDefaultUserRole(String defaultUserRole) {
		this.defaultUserRole = defaultUserRole;
	}

	public String getDefaultAdminRole() {
		return defaultAdminRole;
	}

	public void setDefaultAdminRole(String defaultAdminRole) {
		this.defaultAdminRole = defaultAdminRole;
	}

	public String getPackageDao() {
		return packageDao;
	}

	public void setPackageDao(String packageDao) {
		this.packageDao = packageDao;
	}

	public String getPackageModel() {
		return packageModel;
	}

	public void setPackageModel(String packageModel) {
		this.packageModel = packageModel;
	}

	public String getPackageResource() {
		return packageResource;
	}

	public void setPackageResource(String packageResource) {
		this.packageResource = packageResource;
	}

	public String getPackageApi() {
		return packageApi;
	}

	public void setPackageApi(String packageApi) {
		this.packageApi = packageApi;
	}

	public Boolean getChatable() {
		return chatable;
	}

	public void setChatable(Boolean chatable) {
		this.chatable = chatable;
	}

	public String getCuscss() {
		return cuscss;
	}

	public void setCuscss(String cuscss) {
		this.cuscss = cuscss;
	}

	public String getCusjs() {
		return cusjs;
	}

	public void setCusjs(String cusjs) {
		this.cusjs = cusjs;
	}

	public Boolean getSecure() {
		return secure;
	}

	public void setSecure(Boolean secure) {
		this.secure = secure;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
