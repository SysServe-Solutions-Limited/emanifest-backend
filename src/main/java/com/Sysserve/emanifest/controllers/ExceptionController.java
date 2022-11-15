package com.Sysserve.emanifest.controllers;

import com.Sysserve.emanifest.exception.AccountNotActivatedException;
import com.Sysserve.emanifest.exception.InvalidOTPException;
import com.Sysserve.emanifest.exception.UserAlreadyExistException;
import com.Sysserve.emanifest.exception.UserNotFoundException;
import com.Sysserve.emanifest.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> userNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<?>> userAlreadyExistException(UserAlreadyExistException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidOTPException.class)
    public ResponseEntity<ApiResponse<?>> invalidTokenRequestException(InvalidOTPException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(AccountNotActivatedException.class)
    public ResponseEntity<ApiResponse<?>> accountNotActivatedException(AccountNotActivatedException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.FORBIDDEN), HttpStatus.FORBIDDEN);
    }


}




