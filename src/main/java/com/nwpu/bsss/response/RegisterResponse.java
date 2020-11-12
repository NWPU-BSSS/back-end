package com.nwpu.bsss.response;

public class RegisterResponse {
	
	protected long userId;
	
	public RegisterResponse(long userId) {
		this.userId = userId;
	}
	
	public long getUserId() {
		return this.userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
