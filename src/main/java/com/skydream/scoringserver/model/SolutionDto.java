package com.skydream.scoringserver.model;

public class SolutionDto {
    private String id;
    private String code;

    public SolutionDto() {
    }

    public SolutionDto(String id, String code) {
        this.id = id;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SolutionDto{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
