package com.jialu.minios.vo;

import java.sql.Timestamp;
import java.util.List;

import com.jialu.minios.base.model.MiniUserModel;

public class WsMsg {
	private MiniUserModel from;
	private MiniUserModel to;
	private String type;
	private String content;
	private Timestamp ctime;
	private List<MiniUserModel> live;

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

	public MiniUserModel getFrom() {
		return from;
	}

	public void setFrom(MiniUserModel from) {
		this.from = from;
	}

	public MiniUserModel getTo() {
		return to;
	}

	public void setTo(MiniUserModel to) {
		this.to = to;
	}

	public List<MiniUserModel> getLive() {
		return live;
	}

	public void setLive(List<MiniUserModel> live) {
		this.live = live;
	}
}
