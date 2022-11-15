package com.Sysserve.emanifest.service;

import com.Sysserve.emanifest.dto.LoginDTO;
import com.Sysserve.emanifest.response.ApiResponse;


public interface AuthService {
    ApiResponse<?> login(LoginDTO loginDTO);
    ApiResponse<?> validateOtp(int otpNum);

}
