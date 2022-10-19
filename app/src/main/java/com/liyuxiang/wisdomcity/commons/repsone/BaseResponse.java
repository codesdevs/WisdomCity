package com.liyuxiang.wisdomcity.commons.repsone;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BaseResponse {
    private Integer code;
    private String msg;
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
