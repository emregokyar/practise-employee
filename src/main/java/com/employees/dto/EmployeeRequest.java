package com.employees.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EmployeeRequest {
    @NotBlank(message = "First name is must")
    @Size(min = 2, max = 50, message = "First name is between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name is between 2 and 50 characters")
    @NotBlank(message = "Last name is must")
    private String lastName;

    @NotBlank(message = "Email is must")
    @Email(message = "Please provide a valid email address")
    private String email;

    public EmployeeRequest(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
