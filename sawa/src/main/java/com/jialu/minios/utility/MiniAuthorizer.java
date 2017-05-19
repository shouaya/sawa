package com.jialu.minios.utility;

import com.jialu.minios.vo.OperatorRole;

import io.dropwizard.auth.Authorizer;

public class MiniAuthorizer implements Authorizer<OperatorRole>  {

	@Override
	public boolean authorize(OperatorRole principal, String role) {
		return principal.getRole().toLowerCase().equals(role.toLowerCase());
	}

}
