package com.example.firm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firm.entity.Department;
import com.example.firm.entity.DepartmentEmployee;
import com.example.firm.repository.DepartmentEmployeeRepository;
import com.example.firm.repository.EmployeeRepository;

@Service
public class DepartmentEmployeeServiceImpl implements DepartmentEmployeeService {

    private DepartmentEmployeeRepository departmentEmployeeRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentEmployeeServiceImpl(DepartmentEmployeeRepository departmentEmployeeRepository, EmployeeRepository employeeRepository) {
        this.departmentEmployeeRepository = departmentEmployeeRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void save(DepartmentEmployee departmentEmployee) {
        departmentEmployeeRepository.save(departmentEmployee);
    }

    @Override
    public void changeDepartmentByEmployeeID(Integer employeeId, Department department) {
        DepartmentEmployee updatingDepartmentEmployee;
        Optional<DepartmentEmployee> updatingDepartmentEmployeeOptional = getByEmployeeID(employeeId);

        if (updatingDepartmentEmployeeOptional.isPresent()) {
            updatingDepartmentEmployee = updatingDepartmentEmployeeOptional.get();
            updatingDepartmentEmployee.setDepartment(department);
            departmentEmployeeRepository.save(updatingDepartmentEmployee);
        } else {
            departmentEmployeeRepository.save(new DepartmentEmployee(department, employeeRepository.getById(employeeId)));
        }
    }

    @Override
    public Optional<DepartmentEmployee> getByEmployeeID(Integer id) {
        return departmentEmployeeRepository.getDepartmentEmployeeByEmployee_ID(id);
    }

    @Override
    public void delete(DepartmentEmployee departmentEmployee) {
        departmentEmployeeRepository.delete(departmentEmployee);
    }
}
