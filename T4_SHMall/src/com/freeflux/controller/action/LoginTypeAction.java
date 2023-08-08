package com.freeflux.controller.action;

import com.freeflux.dto.MemberVO;
import com.freeflux.dto.WorkerVO;

class LoginTypeAction {
	
	private String role;
	private MemberVO memberVO;
	private WorkerVO workerVO;
	
	public LoginTypeAction(String role, MemberVO memberVO) {
		this.role = role;
		this.memberVO = memberVO;
	}

	public LoginTypeAction(String role, WorkerVO workerVO) {
		this.role = role;
		this.workerVO = workerVO;
	}
	
	public String getRole() {
		return role;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public WorkerVO getWorkerVO() {
		return workerVO;
	}
	
}
