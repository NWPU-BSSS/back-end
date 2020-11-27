package com.nwpu.bsss.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Project: bsss<br>
 * Created: 3:36 PM, 11/12/2020<br>
 *
 * @author Zejia Lin
 * @version 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyResponseEntity<T> {
	
	protected Code code;
	protected String msg;
	protected T data;
	
	public MyResponseEntity(Code code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public Code getCode() {
		return this.code;
	}
	
	public void setCode(Code code) {
		this.code = code;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public T getData() {
		return this.data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}
