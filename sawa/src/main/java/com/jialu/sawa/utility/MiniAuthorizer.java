package com.jialu.sawa.utility;

import com.jialu.sawa.vo.OperatorRole;

import io.dropwizard.auth.Authorizer;

public class MiniAuthorizer implements Authorizer<OperatorRole>  {

	@Override
	public boolean authorize(OperatorRole principal, String role) {
		return principal.getRole().toLowerCase().equals(role.toLowerCase());
	}

}
