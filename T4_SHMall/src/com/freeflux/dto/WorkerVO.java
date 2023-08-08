package com.freeflux.dto;

import java.sql.Timestamp;

public class WorkerVO {
	private String id;
	private String pwd;
	private String name;
	private String phone;
	private final String logType="worker";

	public WorkerVO() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLogType() {
		return logType;
	}
	
}
