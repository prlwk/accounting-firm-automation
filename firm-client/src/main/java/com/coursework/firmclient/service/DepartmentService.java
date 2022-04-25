package com.coursework.firmclient.service;

import java.util.ArrayList;
import java.util.Optional;

import com.coursework.firmclient.entity.Department;

public interface DepartmentService {
    ArrayList<Department> listDepartments();

    ArrayList<Department> listDepartmentsWithoutProjects();

    ArrayList<Department> listDepartmentsWithoutEmployees();

    Optional<Department> getByID(Integer ID);

    Optional<Department> getByEmployeeID(Integer ID);

    Department save(Department department);

    void delete(Integer id);

    Department update(Integer id, Department department);
}
