package com.Sysserve.emanifest.service;

import com.Sysserve.emanifest.dto.UserDto;
import com.Sysserve.emanifest.exception.InvalidTokenException;
import com.Sysserve.emanifest.model.User;
import com.Sysserve.emanifest.response.ApiResponse;
import com.Sysserve.emanifest.security.CustomUserDetails;

public interface UserService {
    ApiResponse createUser(UserDto dto);

    User getUserByEmail(String email);
    User getUserByFirstName(String firstName);

    User getUserByLastName(String lastName);

     ApiResponse viewAllUsers();

    boolean verifyUser(String token) throws InvalidTokenException;

    void sendConfirmationEmail(User user);

    ApiResponse<String> logout(CustomUserDetails activeUser, String userToken);
}
