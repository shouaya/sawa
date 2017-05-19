package com.jialu.minios.socket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.hibernate.SessionFactory;

import com.jialu.minios.utility.MiniBean;

public class MiniChatSocketServlet extends WebSocketServlet{
	
	private static final long serialVersionUID = 1L;
	
	private MiniBean config;
	private SessionFactory sessionFactory;
	
	
	public MiniChatSocketServlet(MiniBean bean, SessionFactory sessionFactory){
		this.config = bean;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void configure(WebSocketServletFactory factory) {
		// set a 5 second idle timeout
		// factory.getPolicy().setIdleTimeout(10000);
		// register web socket
		factory.setCreator(new MiniSocketCreator(config, this.sessionFactory));
	}

}
