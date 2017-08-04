
package com.jialu.sawa.base.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.jialu.sawa.base.view.StaticView;
import com.jialu.sawa.utility.MiniBean;
import com.jialu.sawa.utility.MiniConstants;
import com.jialu.sawa.utility.MiniResource;

@Path("")
public class GustOption extends MiniResource {

	public GustOption(MiniBean config) {
		super(config);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public Response home() {
		StaticView view = new StaticView("guset.mustache", "guset");
		view.getPage().put("name", config.getName());
		view.getPage().put("host", config.getDebug() ? MiniConstants.DEBUG_HOST : config.getHost());
		view.getPage().put("css", config.getCuscss());
		view.getPage().put("js", config.getCusjs());
		view.setIsDebug(config.getDebug());
		return Response.ok(view).build();
	}
}
