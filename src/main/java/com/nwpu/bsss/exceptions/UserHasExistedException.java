package com.nwpu.bsss.exceptions;

public class UserHasExistedException extends RuntimeException {
    private static final long serialVersionUID = 437902588438864637L;

    public UserHasExistedException(String msg) {
        super(msg);
    }
}
