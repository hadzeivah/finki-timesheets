package com.finki.timesheets.model;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer salary;

    @OneToMany(mappedBy = "project")
    private Set<PositionSalary> salaries;

    public Position() {
    }
    public Position(String name , String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Set<PositionSalary> getSalaries() {
        return salaries;
    }

    public void setSalaries(Set<PositionSalary> salaries) {
        this.salaries = salaries;
    }
}
