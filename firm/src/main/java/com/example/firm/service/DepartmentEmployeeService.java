package com.example.firm.service;

import java.util.Optional;

import com.example.firm.entity.Department;
import com.example.firm.entity.DepartmentEmployee;

public interface DepartmentEmployeeService {
    void save(DepartmentEmployee departmentEmployee);

    void changeDepartmentByEmployeeID(Integer employeeId, Department department);

    Optional<DepartmentEmployee> getByEmployeeID(Integer id);

    void delete(DepartmentEmployee departmentEmployee);
}
