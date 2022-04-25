package com.coursework.firmclient.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.coursework.firmclient.entity.Project;
import com.coursework.firmclient.exception.NonAuthorizedException;
import com.coursework.firmclient.form.ProjectForm;
import com.coursework.firmclient.service.DepartmentService;
import com.coursework.firmclient.service.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    ProjectService projectService;
    DepartmentService departmentService;

    @Autowired
    public ProjectController(ProjectService projectService, DepartmentService departmentService) {
        this.projectService = projectService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String getProjects(Model model) {
        try {
            ArrayList<Project> projects = projectService.listProjects();
            model.addAttribute("projects", projects);
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "projects";
    }

    @GetMapping("/{id}")
    public String getProject(@PathVariable(name = "id") Integer id, Model model) {
        try {
            Optional<Project> project = projectService.getByID(id);
            if (project.isPresent()) {
                model.addAttribute("project", project.get());
                return "project";
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/projects";
    }

    @GetMapping("/new_project/form")
    public String getEmployeeForm(Model model) {
        try {
            model.addAttribute("departments", departmentService.listDepartments());
            model.addAttribute("projectForm", new ProjectForm());
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "new-project";
    }

    @PostMapping("/new_project/add")
    public String addNewProject(@ModelAttribute("projectForm") ProjectForm projectForm) {
        int dateLength = 10;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateStrings = new String[]{projectForm.getDateBegin(), projectForm.getDateEnd(), projectForm.getDateEndReal()};
        Date[] dates = new Date[3];

        for (int i = 0; i < 3; i++) {
            if (dateStrings[i] != null && dateStrings[i].length() == dateLength) {
                try {
                    dates[i] = formatter.parse(dateStrings[i]);
                } catch (ParseException e) {
                    dates[i] = null;
                }
            }
        }

        try {
            Optional<Department> departmentOptional = departmentService.getByID(projectForm.getDepartmentID());
            Department department = null;
            if (departmentOptional.isPresent()) {
                department = departmentOptional.get();
            }

            projectService.save(new Project(projectForm.getName(),
                    projectForm.getCost(),
                    department,
                    dates[0],
                    dates[1],
                    dates[2]));
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/projects";
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable(name = "id") Integer id) {
        try {
            if (projectService.getByID(id).isPresent()) {
                projectService.deleteByID(id);
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/projects";
    }

    @GetMapping("/update_project/form/{id}")
    public String getProjectUpdatingForm(@PathVariable(name = "id") Integer id, Model model) {
        try {
            Optional<Project> project = projectService.getByID(id);
            if (project.isPresent()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                model.addAttribute("departments", departmentService.listDepartments());
                model.addAttribute("projectForm", new ProjectForm(project.get()));
                model.addAttribute("project", project.get());
                model.addAttribute("currentDepartment", project.get().getDepartment());
                if (project.get().getDateBegin() != null) {
                    model.addAttribute("dateBegin", format.format(project.get().getDateBegin()));
                } else {
                    model.addAttribute("dateBegin", "0000-00-00");
                }
                if (project.get().getDateEnd() != null) {
                    model.addAttribute("dateEnd", format.format(project.get().getDateEnd()));
                } else {
                    model.addAttribute("dateEnd", "0000-00-00");
                }
                if (project.get().getDateEndReal() != null) {
                    model.addAttribute("dateEndReal", format.format(project.get().getDateEndReal()));
                } else {
                    model.addAttribute("dateEndReal", "0000-00-00");
                }
                return "update-project";
            }
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/projects" + id;
    }

    @PostMapping("/update/{id}")
    public String updateProject(@ModelAttribute ProjectForm projectForm, @PathVariable(name = "id") Integer id) {
        int dateLength = 10;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String[] dateStrings = new String[]{projectForm.getDateBegin(), projectForm.getDateEnd(), projectForm.getDateEndReal()};
        Date[] dates = new Date[3];

        for (int i = 0; i < 3; i++) {
            if (dateStrings[i] != null && dateStrings[i].length() == dateLength) {
                try {
                    dates[i] = formatter.parse(dateStrings[i]);
                } catch (ParseException e) {
                    dates[i] = null;
                }
            }
        }
        try {
            Optional<Department> departmentOptional = departmentService.getByID(projectForm.getDepartmentID());
            Department department = null;
            if (departmentOptional.isPresent()) {
                department = departmentOptional.get();
            }

            projectService.update(id, new Project(
                    projectForm.getName(),
                    projectForm.getCost(),
                    department,
                    dates[0], dates[1], dates[2]));
        } catch (NonAuthorizedException nonAuthorizedException) {
            return "error-auth";
        }
        return "redirect:/projects/" + id;
    }
}
