package com.jialu.sawa;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Map;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jialu.sawa.vo.OperatorCredentials;

import io.dropwizard.auth.AuthFilter;

@Priority(Priorities.AUTHENTICATION)
public class MiniAuthFilter<P extends Principal> extends AuthFilter<OperatorCredentials, P> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiniAuthFilter.class);

	private MiniAuthFilter() {
    }
	
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		URI uri = context.getUriInfo().getRequestUri();
		LOGGER.debug(uri.toString());
		Map<String, Cookie> cookies = context.getCookies();
		if (cookies.get("uid") == null || cookies.get("token") == null) {
			LOGGER.error(" AUTH: no line cookie");
			throw new ForbiddenException("no line cookie");
		}
		final OperatorCredentials credentials = getCredentials(cookies);
		if (!authenticate(context, credentials, SecurityContext.BASIC_AUTH)) {
			throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
		}
	}

	@Nullable
	private OperatorCredentials getCredentials(Map<String, Cookie> cookies) {
		OperatorCredentials credentials = new OperatorCredentials();
		credentials.setUid(cookies.get("uid").getValue());
		credentials.setToken(cookies.get("token").getValue());
		return credentials;
	}

	public static class Builder<P extends Principal>
			extends AuthFilterBuilder<OperatorCredentials, P, MiniAuthFilter<P>> {

		@Override
		protected MiniAuthFilter<P> newInstance() {
			return new MiniAuthFilter<>();
		}
	}
}
