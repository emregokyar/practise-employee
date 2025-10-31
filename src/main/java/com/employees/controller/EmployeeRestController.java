package com.employees.controller;

import com.employees.dto.EmployeeRequest;
import com.employees.entity.Employee;
import com.employees.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee Rest API endpoints", description = "Operations for employees")
public class EmployeeRestController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve all employees", description = "Retrieve all employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Retrieve an employee", description = "Retrieve a single employee by id")
    public Employee getEmployeeById(@Parameter(description = "Id the of the employee to retrieve")
                                    @PathVariable @Min(value = 1) long employeeId) {
        return employeeService.findById(employeeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new employee", description = "Create a new employee")
    public Employee createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.save(employeeRequest);
    }

    @PutMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an employee", description = "Update an existing employee info")
    public Employee updateEmployee(@Parameter(description = "Id the of the employee to update")
                                   @PathVariable @Min(value = 1) long employeeId,
                                   @Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.update(employeeId, employeeRequest);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an employee", description = "Delete an existing employee info")
    public void deleteEmployee(@Parameter(description = "Id the of the employee to delete")
                               @PathVariable @Min(value = 1) long employeeId) {
        employeeService.deleteById(employeeId);
    }

}
