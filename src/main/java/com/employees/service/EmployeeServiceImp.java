package com.employees.service;

import com.employees.dto.EmployeeRequest;
import com.employees.entity.Employee;
import com.employees.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can not find a user with this id: " + id));
    }

    @Override
    @Transactional
    public Employee save(EmployeeRequest employeeRequest) {
        Employee employee = convertToEmployee(0, employeeRequest);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public Employee update(long id, EmployeeRequest employeeRequest) {
        Employee employee = convertToEmployee(id, employeeRequest);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee convertToEmployee(long id, EmployeeRequest employeeRequest) {
        return new Employee(
                id,
                employeeRequest.getFirstName(),
                employeeRequest.getFirstName(),
                employeeRequest.getEmail());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        employeeRepository.deleteById(id);
    }
}
