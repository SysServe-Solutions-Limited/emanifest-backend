package com.Sysserve.emanifest.controllers;

import com.Sysserve.emanifest.dto.LoginDTO;
import com.Sysserve.emanifest.dto.OtpRequest;
import com.Sysserve.emanifest.exception.InvalidOTPException;
import com.Sysserve.emanifest.exception.UserNotFoundException;
import com.Sysserve.emanifest.response.ApiResponse;
import com.Sysserve.emanifest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO authRequest) throws UserNotFoundException {
        ApiResponse<?> apiResponse = authService.login(authRequest);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @PostMapping("/auth/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody OtpRequest request) throws InvalidOTPException {
        ApiResponse<?> apiResponse = authService.validateOtp(Integer.parseInt(request.getOtp()));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
