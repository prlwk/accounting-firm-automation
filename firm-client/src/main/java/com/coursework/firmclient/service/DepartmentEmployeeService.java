package com.coursework.firmclient.service;

import com.coursework.firmclient.form.DepartmentEmployeeForm;

public interface DepartmentEmployeeService {

    void save(DepartmentEmployeeForm departmentEmployeeForm);

    void changeDepartmentByEmployeeID(Integer employeeId, Integer departmentId);
}
