package com.example.firm.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.firm.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("select e from Employee e " +
            "left join DepartmentEmployee d on e.ID = d.employee.ID " +
            "where d.department.ID = :id")
    ArrayList<Employee> getAllByDepartmentId(@Param("id") Integer id);

    @Query("select e from Employee e " +
            "where not exists (SELECT employee from DepartmentEmployee where employee.ID = e.ID)")
    ArrayList<Employee> findEmployeesNotExistingInDepartmentsEmployees();
}
