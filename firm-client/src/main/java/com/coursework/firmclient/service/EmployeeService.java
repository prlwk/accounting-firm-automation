package com.coursework.firmclient.service;

import java.util.ArrayList;
import java.util.Optional;

import com.coursework.firmclient.entity.Employee;

public interface EmployeeService {
    ArrayList<Employee> listEmployees();

    ArrayList<Employee> listEmployeesWithoutDepartment();

    Optional<Employee> getByID(Integer ID);

    Employee save(Employee employee);

    void delete(Integer id);

    ArrayList<Employee> listEmployeesByDepartmentID(Integer id);

    Employee update(Integer id, Employee employee);
}
