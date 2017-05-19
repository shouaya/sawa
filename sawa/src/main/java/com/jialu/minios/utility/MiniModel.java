package com.jialu.minios.utility;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@Data
public class MiniModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	@JsonIgnore private Timestamp ctime;

	private Integer cuser;
	
	@JsonIgnore private Timestamp utime;

	private Integer uuser;
	
	@JsonIgnore private Integer deleted;
}
