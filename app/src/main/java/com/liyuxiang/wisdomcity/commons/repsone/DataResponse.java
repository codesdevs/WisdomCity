package com.liyuxiang.wisdomcity.commons.repsone;

import java.util.List;

public class DataResponse<T> extends BaseResponse{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
