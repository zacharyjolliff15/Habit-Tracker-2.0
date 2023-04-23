package com.example.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Contact extends AbstractEntity {

    @NotEmpty
    private String habitName = "";

    @NotEmpty
    private String habitDescription = "";

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull
    @JsonIgnoreProperties({"employees"})
    private Company company;

    @NotNull
    @ManyToOne
    private Status status;

  
    @NotEmpty
    private String email = "";

    @Override
    public String toString() {
        return habitName + " " + habitDescription;
    }

    public String gethabitName() {
        return habitName;
    }

    public void sethabitName(String habitName) {
        this.habitName = habitName;
    }

    public String gethabitDescription() {
        return habitDescription;
    }

    public void sethabitDescription(String habitDescription) {
        this.habitDescription = habitDescription;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
