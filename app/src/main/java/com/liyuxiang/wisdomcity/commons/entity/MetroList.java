package com.liyuxiang.wisdomcity.commons.entity;

import java.util.List;

public class MetroList {
    private Integer lineId;
    private String lineName;
    private PreStepDTO preStep;
    private NextStepDTO nextStep;
    private String currentName;
    private Integer reachTime;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public PreStepDTO getPreStep() {
        return preStep;
    }

    public void setPreStep(PreStepDTO preStep) {
        this.preStep = preStep;
    }

    public NextStepDTO getNextStep() {
        return nextStep;
    }

    public void setNextStep(NextStepDTO nextStep) {
        this.nextStep = nextStep;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public Integer getReachTime() {
        return reachTime;
    }

    public void setReachTime(Integer reachTime) {
        this.reachTime = reachTime;
    }

    public static class PreStepDTO {
        private String name;
        private List<LinesDTO> lines;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<LinesDTO> getLines() {
            return lines;
        }

        public void setLines(List<LinesDTO> lines) {
            this.lines = lines;
        }

        public static class LinesDTO {
            private Integer lineId;
            private String lineName;

            public Integer getLineId() {
                return lineId;
            }

            public void setLineId(Integer lineId) {
                this.lineId = lineId;
            }

            public String getLineName() {
                return lineName;
            }

            public void setLineName(String lineName) {
                this.lineName = lineName;
            }
        }
    }

    public static class NextStepDTO {
        private String name;
        private List<LinesDTO> lines;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<LinesDTO> getLines() {
            return lines;
        }

        public void setLines(List<LinesDTO> lines) {
            this.lines = lines;
        }

        public static class LinesDTO {
            private Integer lineId;
            private String lineName;

            public Integer getLineId() {
                return lineId;
            }

            public void setLineId(Integer lineId) {
                this.lineId = lineId;
            }

            public String getLineName() {
                return lineName;
            }

            public void setLineName(String lineName) {
                this.lineName = lineName;
            }
        }
    }
}
