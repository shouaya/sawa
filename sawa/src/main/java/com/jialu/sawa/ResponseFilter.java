
package com.jialu.sawa;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jialu.sawa.utility.OpResult;
import com.jialu.sawa.vo.OperatorResult;


@Provider
public class ResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		int status = responseContext.getStatus();
		if(status > 400){
			OperatorResult<String> or = new OperatorResult<String>();
			or.setCode(OpResult.ERROR.name());
			or.setMsg(String.format("Error: code %s", status));
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(or);
			responseContext.getHeaders().putSingle("Content-Type", "text/html;charset=UTF-8");
			responseContext.setEntity(json);
		}
	}
}
