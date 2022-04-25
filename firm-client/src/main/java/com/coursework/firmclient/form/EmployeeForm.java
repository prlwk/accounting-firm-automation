package com.coursework.firmclient.form;

import com.coursework.firmclient.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeForm {
    private String firstName;
    private String fatherName;
    private String lastName;
    private String position;
    private double salary;
    private Integer departmentID;

    public EmployeeForm(Employee employee, Integer departmentID) {
        this.firstName = employee.getFirstName();
        this.fatherName = employee.getFatherName();
        this.lastName = employee.getLastName();
        this.position = employee.getPosition();
        this.salary = employee.getSalary();
        this.departmentID = departmentID;
    }

    public Employee getEmployee() {
        return new Employee(firstName, fatherName, lastName, position, salary);
    }
}