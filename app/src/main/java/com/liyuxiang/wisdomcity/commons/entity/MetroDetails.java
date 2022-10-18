package com.liyuxiang.wisdomcity.commons.entity;

import java.util.List;

public class MetroDetails {
    private Integer id;
    private String name;
    private String first;
    private String end;
    private String startTime;
    private String endTime;
    private Integer cityId;
    private Object stationsNumber;
    private Integer km;
    private String runStationsName;
    private List<MetroStepListDTO> metroStepList;
    private Integer remainingTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Object getStationsNumber() {
        return stationsNumber;
    }

    public void setStationsNumber(Object stationsNumber) {
        this.stationsNumber = stationsNumber;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }

    public String getRunStationsName() {
        return runStationsName;
    }

    public void setRunStationsName(String runStationsName) {
        this.runStationsName = runStationsName;
    }

    public List<MetroStepListDTO> getMetroStepList() {
        return metroStepList;
    }

    public void setMetroStepList(List<MetroStepListDTO> metroStepList) {
        this.metroStepList = metroStepList;
    }

    public Integer getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Integer remainingTime) {
        this.remainingTime = remainingTime;
    }

    public static class MetroStepListDTO {
        private Object searchValue;
        private Object createBy;
        private String createTime;
        private Object updateBy;
        private String updateTime;
        private Object remark;
        private ParamsDTO params;
        private Integer id;
        private String name;
        private Integer seq;
        private Integer lineId;
        private String firstCh;

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public ParamsDTO getParams() {
            return params;
        }

        public void setParams(ParamsDTO params) {
            this.params = params;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSeq() {
            return seq;
        }

        public void setSeq(Integer seq) {
            this.seq = seq;
        }

        public Integer getLineId() {
            return lineId;
        }

        public void setLineId(Integer lineId) {
            this.lineId = lineId;
        }

        public String getFirstCh() {
            return firstCh;
        }

        public void setFirstCh(String firstCh) {
            this.firstCh = firstCh;
        }

        public static class ParamsDTO {
        }
    }
}
