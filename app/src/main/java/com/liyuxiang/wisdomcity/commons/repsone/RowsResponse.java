package com.liyuxiang.wisdomcity.commons.repsone;

import java.util.Collections;
import java.util.List;

public class RowsResponse<T> extends BaseResponse {
    private List<T> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    private Integer total;

    public List<T> getRows() {
        return rows;
    }


    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
