package com.jialu.minios.utility;

import java.util.Optional;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jialu.minios.base.dao.MiniUserDao;
import com.jialu.minios.base.model.MiniUserModel;
import com.jialu.minios.base.process.UserProcess;
import com.jialu.minios.vo.OperatorCredentials;
import com.jialu.minios.vo.OperatorRole;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;

public class MiniAuthenticator implements Authenticator<OperatorCredentials, OperatorRole> {

	private MiniBean bean;
	private SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(MiniAuthenticator.class);

	public MiniAuthenticator(MiniBean bean, SessionFactory sessionFactory) {
		this.bean = bean;
		this.sessionFactory = sessionFactory;
	}

	@UnitOfWork
	@Override
	public Optional<OperatorRole> authenticate(OperatorCredentials credentials) throws AuthenticationException {

		Session session = getSession();
		try {
			MiniUserDao userDao = this.bean.getDao(MiniUserDao.class);
			MiniUserModel user = UserProcess.findByIdAndToken(userDao, credentials.getUid(), credentials.getToken());

			if (user != null) {
				OperatorRole or = new OperatorRole(user.getName());
				or.setUser(user);
				or.setRole(user.getRole());
				return Optional.of(or);
			}
		} finally {
			session.close();
		}
		LOGGER.error("no auth name: " + credentials.getUid() + " pass:" + credentials.getToken());
		return Optional.empty();
	}

	private Session getSession() {
		Session session = sessionFactory.openSession();
		session.setDefaultReadOnly(true);
		session.setCacheMode(CacheMode.NORMAL);
		session.setFlushMode(FlushMode.MANUAL);
		ManagedSessionContext.bind(session);
		return session;
	}
}
