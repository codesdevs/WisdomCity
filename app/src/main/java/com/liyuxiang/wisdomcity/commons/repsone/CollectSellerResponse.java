package com.liyuxiang.wisdomcity.commons.repsone;

public class CollectSellerResponse extends BaseResponse{
    private Boolean isCollect;
    private Integer id;

    public Boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Boolean isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SellerCollect{" +
                "isCollect=" + isCollect +
                ", id=" + id +
                '}';
    }
}
