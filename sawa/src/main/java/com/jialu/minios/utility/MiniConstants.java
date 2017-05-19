
package com.jialu.minios.utility;

public class MiniConstants {

	public static final String APP_NAME = "minios";

	public static final String COOKIE_OPENID = "openid";

	public static final String COOKIE_AUTH_TOKEN = "mini_token";

	public static final String NOTAUTHORIZEDEXCEPTION = "not allow";

	public static final String WECHAT_USER = "wechat_user";

	public static final String LINKEDIN_AUTHORIZATION_URL = "https://www.linkedin.com/oauth/v2/authorization?response_type=code";
	public static final String LINKEDIN_ACCESSTOKEN_URL = "https://www.linkedin.com/oauth/v2/accessToken";
	public static final String LINKEDIN_SHARE_URL = "https://api.linkedin.com/v1/people/~/shares?format=json";
	public static final String LINKEDIN_PEOPLE_URL = "https://api.linkedin.com/v1/people/~?format=json";

	public static final String GITHUB_AUTHORIZATION_URL = "https://github.com/login/oauth/authorize?response_type=code";
	public static final String GITHUB_ACCESSTOKEN_URL = "https://github.com/login/oauth/access_token";
	public static final String GITHUB_PEOPLE_URL = "https://api.github.com/user";
	
	public static final String AMAZONAWS_S3_URL = "https://s3-ap-northeast-1.amazonaws.com/";
	
	public static final String LINE_REPLY_URL = "https://api.line.me/v2/bot/message/reply";
	public static final String LINE_PUSH_URL = "https://api.line.me/v2/bot/message/push";
	public static final String LINE_MULTICAST_URL = "https://api.line.me/v2/bot/message/multicast";
	public static final String LINE_PROFILE_URL = "https://api.line.me/v2/bot/profile/%s";
	public static final String LINE_CONTENT_URL = "https://api.line.me/v2/bot/message/%s/content";
	
	public static final String PAYPAL_AUTHORIZATION_URL = "https://www.sandbox.paypal.com/signin/authorize";
	public static final String PAYPAL_ACCESSTOKEN_URL = "https://api.line.me/v2/bot/message/%s/content";
	public static final String PAYPAL_PEOPLE_URL = "https://api.github.com/user";

	public static final String MODEL_PACKAGE = "com.jialu.minios.model";
	public static final String RESOURCE_PACKAGE = "com.jialu.minios.resource";
	public static final String DAO_PACKAGE = "com.jialu.minios.dao";
	public static final String BUSINESS_PACKAGE = "com.jialu.minios.business";
	public static final String CONFIGFILE = "config.yml";
	public static final String WEBSOCKET_PATH = "ws";
	public static final String DEBUG_HOST = "localhost";

	public static final String MAPPERPATH = "dozerMappings.xml";
	
	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_USER = "USER";
	
	public static final String LINE_FILE_IMAGE = "image";
	public static final String LINE_FILE_AUDIO = "audio";
	public static final String LINE_FILE_LOCATION = "location";
	public static final String LINE_FILE_VIDEO = "video";
	
	public static final Integer MAX_RECORDER = 10;
}
