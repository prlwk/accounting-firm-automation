package com.coursework.firmclient.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.coursework.firmclient.ServerConfig;
import com.coursework.firmclient.entity.Department;
import com.coursework.firmclient.exception.NonAuthorizedException;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public ArrayList<Department> listDepartments() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList<Department>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "departments",
                    HttpMethod.GET,
                    ServerConfig.createAuthHeaders(),
                    new ParameterizedTypeReference<>() {
                    });
            return response.getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return null;
    }

    @Override
    public ArrayList<Department> listDepartmentsWithoutProjects() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList<Department>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "departments/departments_without/projects",
                    HttpMethod.GET,
                    ServerConfig.createAuthHeaders(),
                    new ParameterizedTypeReference<>() {
                    });
            return response.getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return null;
    }

    @Override
    public ArrayList<Department> listDepartmentsWithoutEmployees() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList<Department>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "departments/departments_without/employees",
                    HttpMethod.GET,
                    ServerConfig.createAuthHeaders(),
                    new ParameterizedTypeReference<>() {
                    });
            return response.getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return null;
    }

    @Override
    public Optional<Department> getByID(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Optional<Department>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "departments/" + id,
                    HttpMethod.GET,
                    ServerConfig.createAuthHeaders(),
                    new ParameterizedTypeReference<>() {
                    });
            return response.getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return Optional.empty();
    }

    @Override
    public Optional<Department> getByEmployeeID(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Optional<Department>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "departments/get_by_employee_id/" + id,
                    HttpMethod.GET,
                    ServerConfig.createAuthHeaders(),
                    new ParameterizedTypeReference<>() {
                    });
            if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new NonAuthorizedException("Method allows authorized user");
            }
            return response.getBody();
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return Optional.empty();
    }

    @Override
    public Department save(Department department) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Department> requestEntity = new HttpEntity<>(department, ServerConfig.createAuthHeaders().getHeaders());
            return restTemplate.postForObject(ServerConfig.getUrl() + "departments/new_department/add",
                    requestEntity, Department.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Optional<Department>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "departments/department/delete/" + id,
                    HttpMethod.DELETE,
                    ServerConfig.createAuthHeaders(),
                    new ParameterizedTypeReference<>() {
                    });
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
    }

    @Override
    public Department update(Integer id, Department department) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Department> requestEntity = new HttpEntity<>(department, ServerConfig.createAuthHeaders().getHeaders());
        Department newDepartment = null;
        try {
            newDepartment = restTemplate.postForObject(ServerConfig.getUrl() + "departments/update/" + id,
                    requestEntity,
                    Department.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return newDepartment;
    }
}
