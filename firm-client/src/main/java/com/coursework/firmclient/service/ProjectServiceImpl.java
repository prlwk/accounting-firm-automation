package com.coursework.firmclient.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.coursework.firmclient.ServerConfig;
import com.coursework.firmclient.entity.Project;
import com.coursework.firmclient.exception.NonAuthorizedException;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public ArrayList<Project> listProjects() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ArrayList<Project>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "projects",
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
    public Optional<Project> getByID(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Optional<Project>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "projects/" + id,
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
    public void save(Project project) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Project> requestEntity = new HttpEntity<>(project, ServerConfig.createAuthHeaders().getHeaders());
            restTemplate.postForObject(ServerConfig.getUrl() + "projects/new_project/add",
                    requestEntity,
                    Project.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
    }

    @Override
    public void deleteByID(Integer id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Optional<Project>> response = restTemplate.exchange(
                    ServerConfig.getUrl() + "projects/project/delete/" + id,
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
    public Project update(Integer id, Project project) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Project> requestEntity = new HttpEntity<>(project, ServerConfig.createAuthHeaders().getHeaders());
            return restTemplate.postForObject(ServerConfig.getUrl() + "projects/update/" + id,
                    requestEntity,
                    Project.class);
        } catch (HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new NonAuthorizedException("Method allows authorized user");
        }
        return null;
    }
}
