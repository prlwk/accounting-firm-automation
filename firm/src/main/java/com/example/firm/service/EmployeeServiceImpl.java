package com.example.firm.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firm.entity.Employee;
import com.example.firm.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArrayList<Employee> listEmployees() {
        return (ArrayList<Employee>) repository.findAll();
    }

    @Override
    public ArrayList<Employee> listEmployeesWithoutDepartment() {
        return repository.findEmployeesNotExistingInDepartmentsEmployees();
    }

    @Override
    public Optional<Employee> getByID(Integer ID) {
        return repository.findById(ID);
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void deleteByID(Integer id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(repository.findById(id).get());
        }
    }

    @Override
    public ArrayList<Employee> listEmployeesFromDepartmentID(Integer id) {
        return repository.getAllByDepartmentId(id);
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        Employee updatingEmployee = null;
        Optional<Employee> updatingEmployeeOptional = repository.findById(id);

        if (updatingEmployeeOptional.isPresent()) {
            updatingEmployee = updatingEmployeeOptional.get();
            updatingEmployee.setFirstName(employee.getFirstName());
            updatingEmployee.setFatherName(employee.getFatherName());
            updatingEmployee.setLastName(employee.getLastName());
            updatingEmployee.setPosition(employee.getPosition());
            updatingEmployee.setSalary(employee.getSalary());
            repository.save(updatingEmployee);
        }

        return updatingEmployee;
    }
}
