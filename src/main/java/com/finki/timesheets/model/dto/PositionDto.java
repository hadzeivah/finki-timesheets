package com.finki.timesheets.model.dto;

public class PositionDto {

    private Long id;
    private String positionType;
    private int salary;

    public PositionDto(String name, int salary) {
        this.positionType = name;
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
