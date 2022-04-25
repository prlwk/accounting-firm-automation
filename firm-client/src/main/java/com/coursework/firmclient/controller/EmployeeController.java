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
import com.coursework.firmclient.entity.Employee;
import com.coursework.firmclient.exception.NonAuthorizedException;
import com.coursework.firmclient.form.DepartmentEmployeeForm;
import com.coursework.firmclient.form.EmployeeForm;
import com.coursework.firmclient.service.DepartmentEmployeeService;
import com.coursework.firmclient.service.DepartmentService;
import com.coursework.firmclient.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeService employeeService;
    DepartmentService departmentService;
    DepartmentEmployeeService departmentEmployeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              DepartmentService departmentService,
                              DepartmentEmployeeService departmentEmployeeService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.departmentEmployeeService = departmentEmployeeService;
    }

    @GetMapping
    public String getEmployees(Model model) {
        try {
            model.addAttribute("employees", employeeService.listEmployees());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "employees";
    }

    @GetMapping("/employees_without/department")
    public String getEmployeesWithoutDepartment(Model model) {
        try {
            model.addAttribute("employees", employeeService.listEmployeesWithoutDepartment());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "employees-without-department";
    }

    @GetMapping("/{id}")
    public String getEmployee(@PathVariable(name = "id") Integer id, Model model) {
        try {
            Optional<Employee> employee = employeeService.getByID(id);
            if (employee.isPresent()) {
                model.addAttribute("employee", employee.get());
                model.addAttribute("department", departmentService.getByEmployeeID(id));
                return "employee";
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/employees";
    }

    @PostMapping("/new_employee/add")
    public String addNewEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        try {
            Employee employee = employeeService.save(employeeForm.getEmployee());
            departmentEmployeeService.save(new DepartmentEmployeeForm(
                    employeeForm.getDepartmentID(), employee.getID()));
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/employees";
    }

    @GetMapping("/new_employee/form")
    public String getEmployeeForm(Model model) {
        try {
            model.addAttribute("departments", departmentService.listDepartments());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        model.addAttribute("employeeForm", new EmployeeForm());
        return "new-employee";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(name = "id") Integer id) {
        try {
            if (employeeService.getByID(id).isPresent()) {
                employeeService.delete(id);
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/employees";
    }

    @GetMapping("/update_employee/form/{id}")
    public String getEmployeeUpdatingForm(@PathVariable(name = "id") Integer id, Model model) {
        try {
            Optional<Employee> employee = employeeService.getByID(id);
            Optional<Department> department = departmentService.getByEmployeeID(id);
            if (employee.isPresent()) {
                model.addAttribute("departments", departmentService.listDepartments());
                model.addAttribute("employeeForm", new EmployeeForm(employee.get(), null));
                model.addAttribute("employee", employee.get());
                model.addAttribute("currentDepartment", department);
                return "update-employee";
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/employees" + id;
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@ModelAttribute EmployeeForm employeeForm, @PathVariable(name = "id") Integer id) {
        try {
            Employee employee = employeeService.update(id, employeeForm.getEmployee());
            departmentEmployeeService.changeDepartmentByEmployeeID(employee.getID(), employeeForm.getDepartmentID());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/employees/" + id;
    }

}
