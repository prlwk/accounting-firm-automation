package com.coursework.firmclient.entity;

import java.util.Date;

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
public class Project {
    private Integer ID;
    private String name;
    private double cost;
    private Department department;
    private Date dateBegin;
    private Date dateEnd;
    private Date dateEndReal;

    public Project(String name, double cost, Department department, Date dateBegin, Date dateEnd, Date dateEndReal) {
        this.name = name;
        this.cost = cost;
        this.department = department;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.dateEndReal = dateEndReal;
    }
}
