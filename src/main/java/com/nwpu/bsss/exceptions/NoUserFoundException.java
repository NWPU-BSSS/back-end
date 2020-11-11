package com.nwpu.bsss.exceptions;

public class NoUserFoundException extends RuntimeException {
	private static final long serialVersionUID = 437902588438864637L;

	public NoUserFoundException(String msg) {
		super(msg);
	}
}
