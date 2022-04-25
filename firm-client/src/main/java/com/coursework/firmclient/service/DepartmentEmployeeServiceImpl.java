package com.coursework.firmclient.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.coursework.firmclient.ServerConfig;
import com.coursework.firmclient.exception.NonAuthorizedException;
import com.coursework.firmclient.form.DepartmentEmployeeForm;

@Service
public class DepartmentEmployeeServiceImpl implements DepartmentEmployeeService {

    @Override
    public void save(DepartmentEmployeeForm departmentEmployeeForm) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Integer> map = new LinkedMultiValueMap<>();
            map.add("departmentID", departmentEmployeeForm.getDepartmentID());
            map.add("employeeID", departmentEmployeeForm.getEmployeeID());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + ServerConfig.getToken());
            HttpEntity<MultiValueMap<String, Integer>> request
                    = new HttpEntity<>(map, headers);
            restTemplate.postForEntity(
                    ServerConfig.getUrl() + "departments_employees/new_department_employee/add",
                    request,
                    Integer.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
    }

    @Override
    public void changeDepartmentByEmployeeID(Integer employeeId, Integer departmentId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Integer> requestEntity = new HttpEntity<>(departmentId, ServerConfig.createAuthHeaders().getHeaders());
            restTemplate.postForObject(
                    ServerConfig.getUrl() +
                            "departments_employees/change_department_by_employee/" +
                            employeeId,
                    requestEntity,
                    Integer.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
    }
}
