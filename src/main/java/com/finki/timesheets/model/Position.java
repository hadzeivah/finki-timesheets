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

    @OneToMany(mappedBy = "project")
    private Set<ProjectPosition> salaries;

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

    public Set<ProjectPosition> getSalaries() {
        return salaries;
    }

    public void setSalaries(Set<ProjectPosition> salaries) {
        this.salaries = salaries;
    }
}
