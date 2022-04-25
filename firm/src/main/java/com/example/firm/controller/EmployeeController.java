package com.example.firm.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.firm.entity.Employee;
import com.example.firm.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Employee>> getEmployees() {
        return new ResponseEntity<>(employeeService.listEmployees(), HttpStatus.OK);
    }

    @GetMapping("/employees_without/department")
    public ResponseEntity<ArrayList<Employee>> getEmployeesWithoutDepartment() {
        return new ResponseEntity<>(employeeService.listEmployeesWithoutDepartment(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getEmployee(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(employeeService.getByID(id), HttpStatus.OK);
    }

    @PostMapping("/new_employee/add")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee) {
        Employee newEmployee = employeeService.save(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/employee/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "id") Integer id) {
        if (employeeService.getByID(id).isPresent()) {
            employeeService.deleteByID(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/from_department/{id}")
    public ResponseEntity<ArrayList<Employee>> getEmployeesFromDepartment(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(employeeService.listEmployeesFromDepartmentID(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.update(id, employee);
        if (updatedEmployee != null) {
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
