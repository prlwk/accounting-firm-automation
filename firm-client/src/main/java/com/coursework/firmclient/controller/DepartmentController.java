package com.coursework.firmclient.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coursework.firmclient.entity.Department;
import com.coursework.firmclient.exception.NonAuthorizedException;
import com.coursework.firmclient.form.DepartmentForm;
import com.coursework.firmclient.service.DepartmentEmployeeService;
import com.coursework.firmclient.service.DepartmentService;
import com.coursework.firmclient.service.EmployeeService;

@RequestMapping("/departments")
@Controller
public class DepartmentController {

    DepartmentService departmentService;
    EmployeeService employeeService;
    DepartmentEmployeeService departmentEmployeeService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService, DepartmentEmployeeService departmentEmployeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.departmentEmployeeService = departmentEmployeeService;
    }

    @GetMapping()
    public String getDepartments(Model model) {
        try {
            model.addAttribute("departments", departmentService.listDepartments());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "departments";
    }

    @GetMapping("/departments_without/projects")
    public String getDepartmentsWithoutProjects(Model model) {
        try {
            model.addAttribute("departments", departmentService.listDepartmentsWithoutProjects());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "departments-without-projects";
    }

    @GetMapping("/departments_without/employees")
    public String getDepartmentsWithoutEmployees(Model model) {
        try {
            model.addAttribute("departments", departmentService.listDepartmentsWithoutEmployees());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "departments-without-employees";
    }

    @GetMapping("/{id}")
    public String getDepartment(@PathVariable(name = "id") Integer id, Model model) {
        try {
            Optional<Department> department = departmentService.getByID(id);
            if (department.isPresent()) {
                model.addAttribute("department", department.get());
                model.addAttribute("employees", employeeService.listEmployeesByDepartmentID(id));
                return "department";
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/departments";
    }

    @GetMapping("/new_department/form")
    public String getDepartmentForm(Model model) {
        model.addAttribute("departmentForm", new DepartmentForm());
        return "new-department";
    }

    @PostMapping("/new_department/add")
    public String addNewDepartment(@ModelAttribute DepartmentForm departmentForm) {
        try {
            departmentService.save(new Department(departmentForm.getName()));
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/departments";
    }

    @PostMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable(name = "id") Integer id) {
        try {
            if (departmentService.getByID(id).isPresent()) {
                departmentService.delete(id);
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/departments";
    }

    @GetMapping("/update_department/form/{id}")
    public String getDepartmentUpdatingForm(@PathVariable(name = "id") Integer id, Model model) {
        try {
            Optional<Department> department = departmentService.getByID(id);
            if (department.isPresent()) {
                model.addAttribute("departmentForm", new DepartmentForm(department.get().getName()));
                model.addAttribute("department", department.get());
                return "update-department";
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/departments" + id;
    }

    @PostMapping("/update/{id}")
    public String updateDepartment(@ModelAttribute DepartmentForm departmentForm,
                                   @PathVariable(name = "id") Integer id) {
        try {
            departmentService.update(id, new Department(departmentForm.getName()));
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/departments/" + id;
    }
}
