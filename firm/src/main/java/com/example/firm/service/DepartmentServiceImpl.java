package com.example.firm.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firm.entity.Department;
import com.example.firm.repository.DepartmentEmployeeRepository;
import com.example.firm.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentEmployeeRepository departmentEmployeeRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentEmployeeRepository departmentEmployeeRepository) {
        this.departmentRepository = departmentRepository;
        this.departmentEmployeeRepository = departmentEmployeeRepository;
    }

    @Override
    public ArrayList<Department> listDepartments() {
        return (ArrayList<Department>) departmentRepository.findAll();
    }

    @Override
    public ArrayList<Department> listDepartmentsWithoutProjects() {
        return departmentRepository.findDepartmentsWithoutProjects();
    }

    @Override
    public ArrayList<Department> listDepartmentsWithoutEmployees() {
        return departmentRepository.findDepartmentsNotExistingInDepartmentsEmployees();
    }

    @Override
    public Optional<Department> getByID(Integer ID) {
        return departmentRepository.findById(ID);
    }

    @Override
    public Optional<Department> getByEmployeeID(Integer id) {
        return departmentRepository.getByEmployeeID(id);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void removeByID(Integer id) {
        if (departmentRepository.findById(id).isPresent()) {
            departmentRepository.delete(departmentRepository.findById(id).get());
        }
    }

    @Override
    public Department update(Integer id, Department department) {
        Department updatingDepartment = null;
        Optional<Department> updatingDepartmentOptional = departmentRepository.findById(id);

        if (updatingDepartmentOptional.isPresent()) {
            updatingDepartment = updatingDepartmentOptional.get();
            updatingDepartment.setName(department.getName());
            departmentRepository.save(updatingDepartment);
        }
        return updatingDepartment;
    }
}
