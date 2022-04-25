package com.example.firm.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.firm.entity.Department;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    @Query("select d from Department d " +
            "left join DepartmentEmployee dp on d.ID = dp.department.ID " +
            "where dp.employee.ID = :id")
    Optional<Department> getByEmployeeID(Integer id);

    @Query("select d from Department d " +
            "where not exists (SELECT department from DepartmentEmployee where department.ID = d.ID)")
    ArrayList<Department> findDepartmentsNotExistingInDepartmentsEmployees();

    @Query("select d from Department d " +
            "where not exists (SELECT department from Project where department.ID = d.ID)")
    ArrayList<Department> findDepartmentsWithoutProjects();
}
