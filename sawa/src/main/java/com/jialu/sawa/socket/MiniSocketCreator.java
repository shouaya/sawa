package com.jialu.sawa.socket;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jialu.sawa.base.dao.MiniUserDao;
import com.jialu.sawa.base.model.MiniUserModel;
import com.jialu.sawa.base.process.UserProcess;
import com.jialu.sawa.utility.MiniBean;

import io.dropwizard.hibernate.UnitOfWork;

public class MiniSocketCreator implements WebSocketCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(MiniSocketCreator.class);

	private SessionFactory sessionFactory;
	private MiniBean bean;

	public MiniSocketCreator(MiniBean bean, SessionFactory sessionFactory) {
		this.bean = bean;
		this.sessionFactory = sessionFactory;
	}

	@Override
	@UnitOfWork
	public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
		Session session = getSession();
		MiniChatSocket socket = new MiniChatSocket();
		try {
			List<HttpCookie> cookies = req.getCookies();
			if (cookies == null || cookies.size() == 0) {
				resp.sendForbidden("no line cookie");
				return null;
			}
			HttpCookie uid = null;
			HttpCookie token = null;
			for (HttpCookie cookie : cookies) {
				if (cookie.getName().equals("uid")) {
					uid = cookie;
				} else if (cookie.getName().equals("token")) {
					token = cookie;
				}
			}
			if (uid == null || token == null) {
				resp.sendForbidden("no line cookie");
				return null;
			}
			MiniUserDao userDao = this.bean.getDao(MiniUserDao.class);
			MiniUserModel user = UserProcess.findByIdAndToken(userDao, uid.getValue(), token.getValue());
			if (user == null) {
				resp.sendForbidden("no line cookie");
				return null;
			}
			socket.setUser(user);
		} catch (IOException e) {
			LOGGER.error("createWebSocket", e);
			return null;
		} finally {
			session.close();
		}
		return socket;
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
