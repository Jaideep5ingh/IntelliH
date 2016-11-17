package com.intelli.h.minor_t2.POJO;

/**
 * Created by YASH on 17-Nov-16.
 */

public class Pin {
    boolean status;
    String pinNo,pinLabel;

    public Pin(boolean status, String pinNo, String pinLabel) {
        this.status = status;
        this.pinNo = pinNo;
        this.pinLabel = pinLabel;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getPinLabel() {
        return pinLabel;
    }

    public void setPinLabel(String pinLabel) {
        this.pinLabel = pinLabel;
    }
}
