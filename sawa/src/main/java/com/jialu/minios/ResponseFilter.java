
package com.jialu.minios;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.jialu.minios.view.StaticView;

@Provider
public class ResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		int status = responseContext.getStatus();
		if(status > 400){
			responseContext.getHeaders().putSingle("Content-Type", "text/html;charset=UTF-8");
			StaticView view = new StaticView("error.mustache", status + "_error");
			responseContext.setEntity(view);
		}
	}
}
