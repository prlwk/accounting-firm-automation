package com.coursework.firmclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

public class ServerConfig {
    private final static String url = "http://localhost:8082/";
    public static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ServerConfig.token = token;
    }

    public static String getUrl() {
        return url;
    }

    public static HttpEntity<MultiValueMap<String, String>> createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (ServerConfig.getToken() != null) {
            headers.add("Authorization", "Bearer " + ServerConfig.getToken());
        }
        return new HttpEntity(headers);
    }
}
