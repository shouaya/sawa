package com.jialu.minios.vo;

public class MiniMeta {
	
	public MiniMeta(){
		
	}
	public MiniMeta(String name, String type, String nullable, String title){
		this.setName(name);
		this.setType(type);
		this.setNullable(nullable);
		this.setTitle(title);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	private String name;
	private String type;
	private String nullable;
	private String title;

}
