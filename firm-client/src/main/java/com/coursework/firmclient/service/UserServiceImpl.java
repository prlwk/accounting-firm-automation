package com.coursework.firmclient.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.coursework.firmclient.ServerConfig;
import com.coursework.firmclient.entity.User;
import com.coursework.firmclient.exception.NonAuthorizedException;
import com.coursework.firmclient.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void signIn(User user) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", user.getUsername());
            map.add("password", user.getPassword());
            HttpEntity<MultiValueMap<String, String>> request
                    = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(ServerConfig.getUrl() + "auth/signin",
                    request,
                    String.class);
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> stringStringMap = null;
            try {
                stringStringMap = mapper.readValue(response.getBody(), Map.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            ServerConfig.setToken(stringStringMap.get("token"));
        } catch (
                HttpClientErrorException httpClientErrorException) {
            if (HttpStatus.FORBIDDEN.equals(httpClientErrorException.getStatusCode()))
                throw new UserNotFoundException("User not found!");
        }
    }
}
