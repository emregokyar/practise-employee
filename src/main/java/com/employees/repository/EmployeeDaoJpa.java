package com.employees.repository;

import com.employees.dao.EmployeeDao;
import com.employees.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoJpa implements EmployeeDao {
    private EntityManager entityManager; // Helps us to create queries

    @Autowired
    public EmployeeDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {

        TypedQuery<Employee> query = entityManager.createQuery("FROM Employee", Employee.class); // Creating a typed query
        return query.getResultList();
    }

    @Override
    public Employee findById(long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public Employee save(Employee employee) {
        // Entity manager creates new record if record id is none. If there's one, it updates the record
        return entityManager.merge(employee);
    }

    @Override
    public void deleteById(long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) entityManager.remove(employee);
    }
}
