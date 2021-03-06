package com.jialu.sawa.base.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jialu.sawa.utility.MiniModel;

/**
 * @author tools
 * ユーザー
 */
@Entity
@Table(name = "mini_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class MiniUserModel extends MiniModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 電話番号
	 */
	@Column(unique = true, nullable = false)
	private java.lang.String phone;

	/**
	 * トークン
	 */
	@JsonIgnore
	@Column(unique = false, nullable = true)
	private java.lang.String token;
		
    /**
	 * パスワード
	 */
	@JsonIgnore
	@Column(unique = false, nullable = true)
	private java.lang.String pass;
		
    /**
	 * 権限
	 */
	@Column(unique = false, nullable = true)
	private java.lang.String role;
}
