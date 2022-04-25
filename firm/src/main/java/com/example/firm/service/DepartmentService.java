package com.example.firm.service;

import java.util.ArrayList;
import java.util.Optional;

import com.example.firm.entity.Department;

public interface DepartmentService {
    ArrayList<Department> listDepartments();

    ArrayList<Department> listDepartmentsWithoutProjects();

    ArrayList<Department> listDepartmentsWithoutEmployees();

    Optional<Department> getByID(Integer id);

    Optional<Department> getByEmployeeID(Integer id);

    Department save(Department department);

    void removeByID(Integer id);

    Department update(Integer id, Department department);
}
