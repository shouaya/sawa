
package com.jialu.minios.resource;

import java.lang.reflect.InvocationTargetException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Cookie;
import com.codahale.metrics.annotation.Timed;
import com.jialu.minios.process.UserProcess;
import com.jialu.minios.utility.MiniBean;
import com.jialu.minios.utility.MiniConstants;
import com.jialu.minios.utility.MiniResource;
import com.jialu.minios.utility.OpResult;
import com.jialu.minios.vo.MiniUser;
import com.jialu.minios.vo.OperatorResult;
import io.dropwizard.hibernate.UnitOfWork;

@Path("option")
public class CustOption extends MiniResource {

	public CustOption(MiniBean config) {
		super(config);
	}

	@GET
	@Timed
	@Path("/get_code")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	public OperatorResult<MiniUser> getCode(@QueryParam("phone") String phone) {
		OperatorResult<MiniUser> msg = new OperatorResult<MiniUser>();
		try {
			msg = UserProcess.sendResgistCode(phone, config);
		} catch (IllegalAccessException | InvocationTargetException e) {
			msg.setCode(OpResult.ERROR.name());
			msg.setMsg("getCode error");
			LOGGER.error("getCode", e);
		}
		return msg;
	}

	@GET
	@Timed
	@Path("/regist")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	public Response regist(@QueryParam("phone") String phone, @QueryParam("code") String code) {
		OperatorResult<MiniUser> msg = new OperatorResult<MiniUser>();
		try {
			msg = UserProcess.regist(phone, code, config);
		} catch (IllegalAccessException | InvocationTargetException e) {
			msg.setCode(OpResult.ERROR.name());
			msg.setMsg("regist error");
			LOGGER.error("getCode", e);
		}
		if (msg.getCode().equals(OpResult.OK.name())) {
			String host = config.getDebug() ? MiniConstants.DEBUG_HOST : config.getHost();
			Boolean secure =  !config.getDebug();
			Cookie cookieUid = new Cookie("uid", msg.getData().getPhone(), "/", host);
			Cookie cookieToken = new Cookie("token", msg.getData().getToken(), "/", host);
			NewCookie cookieU = new NewCookie(cookieUid, null, 86400 * 100, null, secure,
					secure);
			NewCookie cookieT = new NewCookie(cookieToken, null, 86400 * 100, null, secure, secure);
			return Response.ok(msg, MediaType.APPLICATION_JSON).cookie(cookieU, cookieT).build();
		} else {
			return Response.ok(msg, MediaType.APPLICATION_JSON).build();
		}
	}

}
