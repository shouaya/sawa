
package com.jialu.minios.base.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.jialu.minios.base.view.StaticView;
import com.jialu.minios.utility.MiniBean;
import com.jialu.minios.utility.MiniConstants;
import com.jialu.minios.utility.MiniResource;
import io.dropwizard.hibernate.UnitOfWork;

@Path("admin")
public class AdmOption extends MiniResource {

	public AdmOption(MiniBean config) {
		super(config);
	}

	@GET
	@Timed
	@Path("/home")
	@UnitOfWork
	@Produces(MediaType.TEXT_HTML)
	public Response home() {
		StaticView view = new StaticView("admin.mustache", "admin");
		view.getPage().put("maxCount", config.getMaxPost());
		view.getPage().put("name", config.getName());
		view.getPage().put("host", config.getDebug() ? MiniConstants.DEBUG_HOST : config.getHost());
		view.setIsDebug(config.getDebug());
		return Response.ok(view).build();
	}
}
