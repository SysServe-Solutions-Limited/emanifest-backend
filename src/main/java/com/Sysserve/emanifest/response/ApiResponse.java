package com.Sysserve.emanifest.response;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private String message;
    private LocalDateTime timeStamp;
    private T data;


    public ApiResponse(String message, LocalDateTime timeStamp, T data) {
        this.message = message;
        this.timeStamp = LocalDateTime.now();
        this.data = data;
    }

}
