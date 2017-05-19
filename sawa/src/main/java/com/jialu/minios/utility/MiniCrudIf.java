package com.jialu.minios.utility;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jialu.minios.vo.MiniQuery;
import com.jialu.minios.vo.OperatorResult;
import com.jialu.minios.vo.OperatorRole;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;


public abstract interface MiniCrudIf<T extends MiniModel> {

	@GET
	@UnitOfWork
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ MiniConstants.ROLE_ADMIN, MiniConstants.ROLE_USER })
	public abstract OperatorResult<T> getById(@Auth OperatorRole principal, @PathParam("id") Integer id);

	@POST
	@UnitOfWork
	@Path("/query")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ MiniConstants.ROLE_ADMIN, MiniConstants.ROLE_USER })
	public abstract OperatorResult<T> getByQuery(@Auth OperatorRole principal, MiniQuery query);

	@POST
	@UnitOfWork
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ MiniConstants.ROLE_ADMIN, MiniConstants.ROLE_USER })
	public abstract OperatorResult<T> save(@Auth OperatorRole principal, T json);

	@POST
	@UnitOfWork
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ MiniConstants.ROLE_ADMIN })
	public abstract OperatorResult<T> delete(@Auth OperatorRole principal, @PathParam("id") Integer id);

	@POST
	@UnitOfWork
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ MiniConstants.ROLE_ADMIN, MiniConstants.ROLE_USER })
	public abstract OperatorResult<List<T>> list(@Auth OperatorRole principal, MiniQuery query);
}
