package com.jialu.minios.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jialu.minios.process.ChatProcess;
import com.jialu.minios.vo.WsMsg;
import com.jialu.minios.vo.WsUser;

public class MiniChatSocketBroadcaster {
	private static MiniChatSocketBroadcaster INSTANCE = new MiniChatSocketBroadcaster();
	private HashMap<Integer, MiniChatSocket> clients = new HashMap<Integer, MiniChatSocket>();

	private ObjectMapper mapper = new ObjectMapper();

	private MiniChatSocketBroadcaster() {
	}

	protected static MiniChatSocketBroadcaster getInstance() {
		return INSTANCE;
	}

	/**
	 * Add Client
	 * 
	 * @throws JsonProcessingException
	 */
	protected void join(MiniChatSocket socket) throws JsonProcessingException {
		clients.put(socket.getUser().getId(), socket);
		WsMsg msg = ChatProcess.mappingMessage(ChatProcess.WsMsgType.JOIN, socket.getUser(), null);
		send(msg);
	}

	/**
	 * Delete Client
	 * 
	 * @throws JsonProcessingException
	 */
	protected void bye(MiniChatSocket socket) throws JsonProcessingException {
		clients.remove(socket.getUser().getId(), socket);
		WsMsg msg = ChatProcess.mappingMessage(ChatProcess.WsMsgType.LEAVE, socket.getUser(), null);
		send(msg);
	}

	/**
	 * Send Message
	 * 
	 * @param message
	 * @throws JsonProcessingException
	 */
	protected void send(WsMsg message) throws JsonProcessingException {
		if (message.getTo() == null) {
			for (Integer uid : clients.keySet()) {
				MiniChatSocket socket = clients.get(uid);
				if (message.getType().equals(ChatProcess.WsMsgType.JOIN.name())
						|| message.getType().equals(ChatProcess.WsMsgType.LEAVE.name())) {
					message.setLive(getLive(uid));
				}
				socket.getSession().getRemote().sendStringByFuture(mapper.writeValueAsString(message));
			}
		} else {
			MiniChatSocket socket = clients.get(message.getTo().getId());
			if (socket != null) {
				socket.getSession().getRemote().sendStringByFuture(mapper.writeValueAsString(message));
			}
		}
	}

	/**
	 * @return
	 */
	private List<WsUser> getLive(Integer self) {
		List<WsUser> live = new ArrayList<WsUser>();
		for (Integer uid : clients.keySet()) {
			if(uid == self){
				continue;
			}
			live.add(clients.get(uid).getUser());
		}
		return live;
	}
}
