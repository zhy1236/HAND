package com.example.hand.mockingbot.entity;

/**
 * Created by zhy on 2017/5/18.
 */

public class ErrorBean {
    private String message;
    private String type;
    private int code;
    private String trace_id;
    private int error_subcode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }

    public int getError_subcode() {
        return error_subcode;
    }

    public void setError_subcode(int error_subcode) {
        this.error_subcode = error_subcode;
    }
}
