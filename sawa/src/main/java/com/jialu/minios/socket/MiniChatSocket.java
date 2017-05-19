package com.jialu.minios.socket;

import java.io.IOException;
import java.sql.Timestamp;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jialu.minios.process.ChatProcess;
import com.jialu.minios.vo.WsMsg;
import com.jialu.minios.vo.WsUser;

@WebSocket
public class MiniChatSocket {

	private Session session;

	private WsUser user;
	
	private ObjectMapper mapper = new ObjectMapper();

	public MiniChatSocket() {
	}

	@OnWebSocketConnect
	public void onConnect(Session session) throws JsonProcessingException {
		this.session = session;
		MiniChatSocketBroadcaster.getInstance().join(this);
	}

	@OnWebSocketMessage
	public void onText(String message) throws JsonParseException, JsonMappingException, IOException {
		WsMsg msg = mapper.readValue(message, WsMsg.class);
		msg.setFrom(this.user);
		msg.setType(ChatProcess.WsMsgType.CHAT.name());
		msg.setCtime(new Timestamp(System.currentTimeMillis()));
		MiniChatSocketBroadcaster.getInstance().send(msg);
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) throws JsonProcessingException {
		MiniChatSocketBroadcaster.getInstance().bye(this);
	}

	@OnWebSocketError
	public void onError(Throwable cause) throws JsonProcessingException {
		MiniChatSocketBroadcaster.getInstance().bye(this);
		cause.printStackTrace(System.err);
	}

	public Session getSession() {
		return this.session;
	}

	public WsUser getUser() {
		return user;
	}

	public void setUser(WsUser user) {
		this.user = user;
	}
}
