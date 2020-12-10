package com.nwpu.bsss.response;

import lombok.Getter;
import lombok.Setter;


public class UserSubscribeStatusResponse {
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
