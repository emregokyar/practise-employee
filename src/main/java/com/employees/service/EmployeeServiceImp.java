package com.employees.service;

import com.employees.dao.EmployeeDao;
import com.employees.dto.EmployeeRequest;
import com.employees.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService {
    private EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImp(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public Employee findById(long id) {
        return employeeDao.findById(id);
    }

    @Override
    @Transactional
    public Employee save(EmployeeRequest employeeRequest) {
        Employee employee = convertToEmployee(0, employeeRequest);
        return employeeDao.save(employee);
    }

    @Override
    @Transactional
    public Employee update(long id, EmployeeRequest employeeRequest) {
        Employee employee = convertToEmployee(id, employeeRequest);
        return employeeDao.save(employee);
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
        employeeDao.deleteById(id);
    }
}
