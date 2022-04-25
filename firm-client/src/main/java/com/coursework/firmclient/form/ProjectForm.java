package com.coursework.firmclient.form;

import com.coursework.firmclient.entity.Project;

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
public class ProjectForm {
    private String name;
    private double cost;
    private Integer departmentID;
    private String dateBegin;
    private String dateEnd;
    private String dateEndReal;

    public ProjectForm(Project project) {
        this.name = project.getName();
        this.cost = project.getCost();
        this.departmentID = project.getDepartment().getID();
        this.dateBegin = String.valueOf(project.getDateBegin());
        this.dateEnd = String.valueOf(project.getDateEnd());
        this.dateEndReal = String.valueOf(project.getDateEndReal());
    }
}
