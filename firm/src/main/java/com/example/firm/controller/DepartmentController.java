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
import org.springframework.web.client.HttpClientErrorException;

import com.example.firm.entity.Department;
import com.example.firm.service.DepartmentService;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Department>> getDepartments() {
        return new ResponseEntity<>(departmentService.listDepartments(), HttpStatus.OK);
    }

    @GetMapping("/departments_without/projects")
    public ResponseEntity<ArrayList<Department>> getDepartmentsWithoutProject() {
        return new ResponseEntity<>(departmentService.listDepartmentsWithoutProjects(), HttpStatus.OK);
    }

    @GetMapping("/departments_without/employees")
    public ResponseEntity<ArrayList<Department>> getDepartmentsWithoutEmployees() {
        return new ResponseEntity<>(departmentService.listDepartmentsWithoutEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Department>> getDepartment(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(departmentService.getByID(id), HttpStatus.OK);
    }

    @GetMapping("/get_by_employee_id/{id}")
    public ResponseEntity<Optional<Department>> addNewEmployee(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(departmentService.getByEmployeeID(id), HttpStatus.CREATED);
    }

    @PostMapping("/new_department/add")
    public ResponseEntity<Department> addNewDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.save(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/department/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteDepartment(@PathVariable(name = "id") Integer id) {
        if (departmentService.getByID(id).isPresent()) {
            departmentService.removeByID(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") Integer id, @RequestBody Department department) {
        Department updatedDepartment = departmentService.update(id, department);
        if (updatedDepartment != null) {
            return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
