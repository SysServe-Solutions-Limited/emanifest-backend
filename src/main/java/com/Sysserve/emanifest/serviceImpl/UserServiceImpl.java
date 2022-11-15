package com.Sysserve.emanifest.serviceImpl;

import com.Sysserve.emanifest.dto.UserDto;
import com.Sysserve.emanifest.eventlisteners.OnUserLogoutSuccessListener;
import com.Sysserve.emanifest.exception.InvalidTokenException;
import com.Sysserve.emanifest.exception.UserAlreadyExistException;
import com.Sysserve.emanifest.exception.UserNotFoundException;
import com.Sysserve.emanifest.model.AccountVerificationEmailContext;
import com.Sysserve.emanifest.model.ConfirmationToken;
import com.Sysserve.emanifest.model.User;
import com.Sysserve.emanifest.repository.ConfirmationTokenRepository;
import com.Sysserve.emanifest.repository.UserRepository;
import com.Sysserve.emanifest.response.ApiResponse;
import com.Sysserve.emanifest.security.CustomUserDetails;
import com.Sysserve.emanifest.service.ConfirmationTokenService;
import com.Sysserve.emanifest.service.EmailService;
import com.Sysserve.emanifest.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OTPService otpService;

    private final EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final String baseURL;

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
            throw new UserAlreadyExistException("User Already exist");
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

    @Override
    public boolean verifyUser(String token) throws InvalidTokenException {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
        if(Objects.isNull(confirmationToken) ||  confirmationToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        User user = userRepository.getOne(confirmationToken.getUser().getId());
        if(Objects.isNull(user)){
            return false;
        }
        user.setAccountVerified(true);
        userRepository.save(user); // let's same user details

        // we don't need invalid password now
        confirmationTokenService.removeToken(confirmationToken);
        return true;
    }
    @Override
    public void sendConfirmationEmail(User user) {
        ConfirmationToken confirmationToken = confirmationTokenService.createConfirmationToken();
        confirmationToken.setUser(user);
        confirmationTokenRepository.save(confirmationToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();

        emailContext.init(user);
        emailContext.setToken(confirmationToken.getToken());
        emailContext.buildVerificationUrl(baseURL, confirmationToken.getToken());
        emailService.sendSimpleAuthMail(emailContext);
    }



    @Override
    public ApiResponse<String> logout(CustomUserDetails activeUser, String userToken) {

        String token = userToken.substring(7);
        OnUserLogoutSuccessListener successListener = new OnUserLogoutSuccessListener(activeUser.getUsername(), token);
        applicationEventPublisher.publishEvent(successListener);

        //Remove the recently used OTP from server.
        otpService.clearOTP(activeUser.getUsername());

        String response = activeUser.getUsername() + " was successfully logged out";

        return new ApiResponse<>("success", LocalDateTime.now(), response);
    }


}