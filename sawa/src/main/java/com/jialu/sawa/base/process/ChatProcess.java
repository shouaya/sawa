package com.jialu.sawa.base.process;

import java.sql.Timestamp;

import com.jialu.sawa.base.model.MiniUserModel;
import com.jialu.sawa.vo.WsMsg;

public class ChatProcess {

	public enum WsMsgType {
		JOIN, CHAT, LEAVE;
	}

	public static WsMsg mappingMessage(WsMsgType type, MiniUserModel from, MiniUserModel to) {
		WsMsg msg = new WsMsg();
		msg.setType(type.name());
		msg.setCtime(new Timestamp(System.currentTimeMillis()));
		msg.setFrom(from);
		msg.setTo(to);
		return msg;
	}
}
