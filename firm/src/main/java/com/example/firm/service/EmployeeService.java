package com.example.firm.service;

import java.util.ArrayList;
import java.util.Optional;

import com.example.firm.entity.Employee;

public interface EmployeeService {
    ArrayList<Employee> listEmployees();

    ArrayList<Employee> listEmployeesWithoutDepartment();

    Optional<Employee> getByID(Integer id);

    Employee save(Employee employee);

    void deleteByID(Integer id);

    ArrayList<Employee> listEmployeesFromDepartmentID(Integer id);

    Employee update(Integer id, Employee employee);
}
