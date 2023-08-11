package com.schoolmanagement.controller.user;

import com.schoolmanagement.payload.request.LoginRequest;
import com.schoolmanagement.payload.response.AuthResponse;
import com.schoolmanagement.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login") // http://localhost:8080/auth/login   + POST
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return authenticationService.authenticateUser(loginRequest);
    }
}