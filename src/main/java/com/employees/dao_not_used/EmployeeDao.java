package com.employees.dao_not_used;

import com.employees.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> findAll();

    Employee findById(long id);

    Employee save(Employee employee);

    void deleteById(long id);
}
