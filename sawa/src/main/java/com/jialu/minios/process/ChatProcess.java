package com.jialu.minios.process;

import java.sql.Timestamp;

import com.jialu.minios.model.MiniUserModel;
import com.jialu.minios.vo.WsMsg;
import com.jialu.minios.vo.WsUser;

public class ChatProcess {

	public enum WsMsgType {
		JOIN, CHAT, LEAVE;
	}

	public static WsUser mappingUser(MiniUserModel userModel) {
		WsUser user = new WsUser();
		user.setId(userModel.getId());
		user.setName(userModel.getName());
		user.setAvatar(userModel.getAvatar());
		user.setTitle(userModel.getTitle());
		return user;
	}

	public static WsMsg mappingMessage(WsMsgType type, WsUser from, WsUser to) {
		WsMsg msg = new WsMsg();
		msg.setType(type.name());
		msg.setCtime(new Timestamp(System.currentTimeMillis()));
		msg.setFrom(from);
		msg.setTo(to);
		return msg;
	}
}
