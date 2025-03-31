package com.task.TaskManagement.service;

import com.task.TaskManagement.dto.AuthResponseDTO;
import com.task.TaskManagement.model.User;
import com.task.TaskManagement.repository.UserRepository;
import com.task.TaskManagement.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO register(AuthResponseDTO Auth){
        AuthResponseDTO authRes = new AuthResponseDTO();
        try {
            User user = new User();
            user.setUsername(Auth.getUsername());
            user.setPassword(passwordEncoder.encode(Auth.getPassword()));
            user.setEmail(Auth.getEmail());
            user.setRole(Auth.getRole());
            User ourUserResult = userRepository.save(user);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                authRes.setUser(ourUserResult);
                authRes.setMessage("User Saved Successfully");
                authRes.setStatusCode(200);
            }
        }catch (Exception e){
            authRes.setStatusCode(500);
            authRes.setError(e.getMessage());
        }
        return authRes;
    }

    public AuthResponseDTO login(AuthResponseDTO Auth){
        AuthResponseDTO authRes = new AuthResponseDTO();
        try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(Auth.getUsername(),Auth.getPassword()));
            var user = userRepository.findByUsername(Auth.getUsername()).orElseThrow();
            System.out.print("User is "+ user);
            var jwt = jwtUtil.generateToken(user);
            var refreshToken = jwtUtil.generateRefreshToken(new HashMap<>(),user);
            authRes.setStatusCode(200);
            authRes.setToken(jwt);
            authRes.setRefreshToken(refreshToken);
            authRes.setExpirationTime("24Hr");
            authRes.setMessage("Successfully Signed In");
        }catch (Exception e){
            authRes.setStatusCode(500);
            authRes.setError(e.getMessage());
        }
        return authRes;
    }

    public AuthResponseDTO refreshToken(AuthResponseDTO refToken){
        AuthResponseDTO response = new AuthResponseDTO();
        String ourUsername = jwtUtil.extractUsername(refToken.getToken());
        User users = userRepository.findByUsername(ourUsername).orElseThrow();
        if (jwtUtil.validateToken(refToken.getToken(), users)) {
            var jwt = jwtUtil.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refToken.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }

}
