package com.example.joinq.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {

    Map<String, Object> loginUser(String username, String password);
}
