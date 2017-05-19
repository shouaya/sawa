package com.jialu.minios.vo;

import java.sql.Timestamp;
import java.util.List;

public class WsMsg {
	private WsUser from;
	private WsUser to;
	private String type;
	private String content;
	private Timestamp ctime;
	private List<WsUser> live;

	public Timestamp getCtime() {
		return ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public WsUser getFrom() {
		return from;
	}

	public void setFrom(WsUser from) {
		this.from = from;
	}

	public WsUser getTo() {
		return to;
	}

	public void setTo(WsUser to) {
		this.to = to;
	}

	public List<WsUser> getLive() {
		return live;
	}

	public void setLive(List<WsUser> live) {
		this.live = live;
	}
}
