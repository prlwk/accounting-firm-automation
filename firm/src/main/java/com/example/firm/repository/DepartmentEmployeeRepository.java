package com.example.firm.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.firm.entity.DepartmentEmployee;

public interface DepartmentEmployeeRepository extends CrudRepository<DepartmentEmployee, Integer> {

    Optional<DepartmentEmployee> getDepartmentEmployeeByEmployee_ID(Integer ID);
}