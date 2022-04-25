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
import com.coursework.firmclient.entity.Employee;
import com.coursework.firmclient.exception.NonAuthorizedException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public ArrayList<Employee> listEmployees() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList<Employee>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "employees",
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
    public ArrayList<Employee> listEmployeesWithoutDepartment() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList<Employee>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "employees/employees_without/department",
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
        return null;
    }

    @Override
    public Optional<Employee> getByID(Integer ID) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Optional<Employee>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "employees/" + ID,
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
    public Employee save(Employee employee) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, ServerConfig.createAuthHeaders().getHeaders());
            return restTemplate.postForObject(ServerConfig.getUrl() + "employees/new_employee/add",
                    requestEntity,
                    Employee.class);
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
            ResponseEntity<Optional<Employee>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "employees/employee/delete/" + id,
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
    public ArrayList<Employee> listEmployeesByDepartmentID(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList<Employee>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "employees/from_department/" + id,
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
        return null;
    }

    @Override
    public Employee update(Integer id, Employee employee) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Employee> requestEntity = new HttpEntity<>(employee, ServerConfig.createAuthHeaders().getHeaders());
            return restTemplate.postForObject(ServerConfig.getUrl() + "employees/update/" + id,
                    requestEntity,
                    Employee.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return null;
    }
}
