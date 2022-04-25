package com.example.firm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.firm.entity.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
