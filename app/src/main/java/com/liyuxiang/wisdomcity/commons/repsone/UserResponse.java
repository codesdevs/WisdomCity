package com.liyuxiang.wisdomcity.commons.repsone;

public class UserResponse<T> extends BaseResponse{
    private T user;

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }
}
