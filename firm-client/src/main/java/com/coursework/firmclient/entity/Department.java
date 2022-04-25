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
public class Department {
    private Integer ID;
    private String name;
    public Department(String departmentName) {
        this.name = departmentName;
    }
}
