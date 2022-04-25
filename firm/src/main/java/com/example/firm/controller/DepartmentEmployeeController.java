package com.example.firm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.firm.entity.Department;
import com.example.firm.entity.DepartmentEmployee;
import com.example.firm.entity.Employee;
import com.example.firm.service.DepartmentEmployeeService;
import com.example.firm.service.DepartmentService;
import com.example.firm.service.EmployeeService;

@Controller
@RequestMapping("/departments_employees")
public class DepartmentEmployeeController {
    DepartmentEmployeeService departmentEmployeeService;
    DepartmentService departmentService;
    EmployeeService employeeService;

    @Autowired
    public DepartmentEmployeeController(DepartmentEmployeeService departmentEmployeeService, DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentEmployeeService = departmentEmployeeService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @PostMapping(value = "/new_department_employee/add")
    public ResponseEntity<DepartmentEmployee> addNewEmployee(@RequestBody LinkedMultiValueMap<String, Integer> map) {
        Optional<Department> department = departmentService.getByID(map.getFirst("departmentID"));
        Optional<Employee> employee = employeeService.getByID(map.getFirst("employeeID"));

        if (department.isPresent() && employee.isPresent()) {
            departmentEmployeeService.save(new DepartmentEmployee(department.get(), employee.get()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change_department_by_employee/{id}")
    public ResponseEntity<DepartmentEmployee> changeDepartmentByEmployeeId(@RequestBody Integer departmentId,
                                                                           @PathVariable Integer id) {
        if (departmentService.getByID(departmentId).isPresent()) {
            departmentEmployeeService.changeDepartmentByEmployeeID(id, departmentService.getByID(departmentId).get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/department_employee/delete_by_employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDepartmentEmployee(@PathVariable(name = "id") Integer id) {
        Optional<Employee> employee = employeeService.getByID(id);
        if (employee.isPresent()) {
            Optional<DepartmentEmployee> departmentEmployee = departmentEmployeeService.getByEmployeeID(id);
            if (departmentEmployee.isPresent()) {
                departmentEmployeeService.delete(departmentEmployee.get());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
