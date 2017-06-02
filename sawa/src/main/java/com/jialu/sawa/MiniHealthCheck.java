
package com.jialu.sawa;

import org.hibernate.SessionFactory;

import com.codahale.metrics.health.HealthCheck;

public class MiniHealthCheck extends HealthCheck {

	private SessionFactory sessionFactory;

	public MiniHealthCheck(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	protected Result check() throws Exception {
		if (sessionFactory.isClosed()) {
			return Result.unhealthy(" Session Factory is close");
		}
		return Result.healthy();
	}
}
