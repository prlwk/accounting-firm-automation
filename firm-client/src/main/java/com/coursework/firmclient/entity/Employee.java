package com.coursework.firmclient.entity;

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
public class Employee {
    private Integer ID;
    private String firstName;
    private String fatherName;
    private String lastName;
    private String position;
    private double salary;

    public Employee(String firstName, String fatherName, String lastName, String position, double salary) {
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
    }
}
