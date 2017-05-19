package com.jialu.minios.vo;

import java.util.List;

public class MiniQuery {

	private String name;
	private List<MiniPair> params;
	private Integer limit;
	private Integer offset;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public List<MiniPair> getParams() {
		return params;
	}
	public void setParams(List<MiniPair> params) {
		this.params = params;
	}
}
