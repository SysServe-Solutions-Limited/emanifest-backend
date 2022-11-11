package com.Sysserve.emanifest.serviceImpl;

import com.Sysserve.emanifest.dto.UserDto;
import com.Sysserve.emanifest.exception.UserAlreadyExistException;
import com.Sysserve.emanifest.exception.UserNotFoundException;
import com.Sysserve.emanifest.model.User;
import com.Sysserve.emanifest.repository.UserRepository;
import com.Sysserve.emanifest.response.ApiResponse;
import com.Sysserve.emanifest.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<User> createUser(UserDto dto) {
        String email = dto.getEmail();
        Optional<User> existingUser = userRepository.findUserByEmail(email);

        if (existingUser.isEmpty()) {
            User user = User.builder()
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .userName(dto.getUserName())
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .role(dto.getRole())
                    .phoneNo(dto.getPhoneNo())
                    .build();
            userRepository.save(user);
            return new ApiResponse<>("success", LocalDateTime.now(), user);
        } else {
            throw new UserAlreadyExistException("user Already exist");
        }

    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email + "not found"));
    }

    @Override
    public User getUserByFirstName(String firstName) {
        return userRepository.findUserByFirstName(firstName).orElseThrow(() -> new UserNotFoundException(firstName + "Not found"));
    }

    @Override
    public User getUserByLastName(String lastName) {
        return userRepository.findUserByLastName(lastName).orElseThrow(() -> new UserNotFoundException(lastName + "Not found"));
    }

    @Override
    public ApiResponse viewAllUsers(){
        List<User> allUsers = userRepository.findAll();
        return new ApiResponse<>("success", LocalDateTime.now(), allUsers);

    }




}