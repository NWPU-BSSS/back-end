package com.nwpu.bsss.response;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status code
 */
public enum Code {
	OK(1),
	BAD_OPERATION(0),
	BAD_REQUEST(-1);
	
	private int code;
	
	Code(int code) {
		this.code = code;
	}
	
	@JsonValue
	public int getCode() {
		return this.code;
	}
}
