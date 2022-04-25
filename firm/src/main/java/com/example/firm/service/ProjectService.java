package com.example.firm.service;

import java.util.ArrayList;
import java.util.Optional;

import com.example.firm.entity.Project;

public interface ProjectService {
    ArrayList<Project> listProjects();

    Optional<Project> getByID(Integer id);

    void save(Project project);

    void deleteByID(Integer id);

    Project update(Integer id, Project project);
}
