package com.jialu.sawa.utility;

import com.jialu.sawa.vo.OperatorRole;

import io.dropwizard.auth.Authorizer;

public class MiniAuthorizer implements Authorizer<OperatorRole>  {

	@Override
	public boolean authorize(OperatorRole principal, String role) {
		String[] roles = role.split(",");
		for(String r : roles){
			if(principal.getRole().toLowerCase().equals(r.toLowerCase())){
				return true;
			}
		}
		return false;
	}

}
