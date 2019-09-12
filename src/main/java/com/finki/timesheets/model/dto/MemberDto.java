package com.finki.timesheets.model.dto;

import com.finki.timesheets.model.Position;
import com.finki.timesheets.model.Project;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.Set;

public class MemberDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String embg;
    private String transactionAccount;
    @Enumerated(EnumType.STRING)
    private Position position;
    private Set<Project> projects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmbg() {
        return embg;
    }

    public void setEmbg(String embg) {
        this.embg = embg;
    }

    public String getTransactionAccount() {
        return transactionAccount;
    }

    public void setTransactionAccount(String transactionAccount) {
        this.transactionAccount = transactionAccount;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
