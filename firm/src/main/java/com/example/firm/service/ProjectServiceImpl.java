package com.example.firm.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.firm.entity.Project;
import com.example.firm.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository repository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArrayList<Project> listProjects() {
        return (ArrayList<Project>) repository.findAll();
    }

    @Override
    public Optional<Project> getByID(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void save(Project project) {
        repository.save(project);
    }

    @Override
    public void deleteByID(Integer id) {
        if (repository.findById(id).isPresent()) {
            repository.delete(repository.findById(id).get());
        }
    }

    @Override
    public Project update(Integer id, Project project) {
        Project updatingProject = null;
        Optional<Project> updatingProjectOptional = repository.findById(id);

        if (updatingProjectOptional.isPresent()) {
            updatingProject = updatingProjectOptional.get();
            updatingProject.setName(project.getName());
            updatingProject.setCost(project.getCost());
            updatingProject.setDateBegin(project.getDateBegin());
            updatingProject.setDateEnd(project.getDateEnd());
            updatingProject.setDateEndReal(project.getDateEndReal());
            updatingProject.setDepartment(project.getDepartment());
            repository.save(updatingProject);
        }

        return updatingProject;
    }
}
