package com.nwpu.bsss.exceptions;

public class NoFileFoundException extends RuntimeException {
    private static final long serialVersionUID = 437902588438864637L;

    public NoFileFoundException(String msg) {
        super(msg);
    }
}
