package com.Sysserve.emanifest.serviceImpl;

import com.Sysserve.emanifest.dto.EmailDetailsRequest;
import com.Sysserve.emanifest.dto.LoginDTO;
import com.Sysserve.emanifest.dto.PrincipalDto;
import com.Sysserve.emanifest.exception.AccountNotActivatedException;
import com.Sysserve.emanifest.exception.InvalidOTPException;
import com.Sysserve.emanifest.exception.UserNotFoundException;
import com.Sysserve.emanifest.model.User;
import com.Sysserve.emanifest.repository.UserRepository;
import com.Sysserve.emanifest.response.ApiResponse;
import com.Sysserve.emanifest.security.JwtUtil;
import com.Sysserve.emanifest.service.AuthService;
import com.Sysserve.emanifest.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final OTPService otpService;

    @Override
    public ApiResponse<?> login(LoginDTO loginDTO) {
        Optional<User> existingUser = Optional.of(userRepository.findUserByEmail(loginDTO.getEmail()).get());
        if (!existingUser.get().isAccountVerified()) {
            throw new AccountNotActivatedException("Account is not activated");
        } else {
            Authentication authenticate;
            try {
                authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
                );
            } catch (AuthenticationException ex) {
                log.error(ex.getMessage());
                throw new UserNotFoundException("Invalid username or password");
            }
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            System.out.println(authenticate.getPrincipal().toString());
            User user = existingUser.get();
            sendOTPEmail(user);
            return new ApiResponse<>("success", LocalDateTime.now(), jwtUtil.generateToken(existingUser.get().getEmail()));
        }
    }

    private void sendOTPEmail(User user) {
        int OTP = otpService.generateOTP(user.getEmail());
        log.info("OTP " + OTP);
        String msgBody = "Dear " + user.getFirstName() + "," + "\n\nPlease enter this OTP (" + OTP + ")" + " to have access to the eManifest application." + "\n"
                + "\n\nThank you!";
        String subject = "One Time Password";
        EmailDetailsRequest emailDetailsRequest = new EmailDetailsRequest(user.getEmail(), msgBody, subject);
        emailService.sendSimpleMail(emailDetailsRequest);
        log.info("OTP sent to " + user.getEmail());
    }

    @Override
    public ApiResponse<?> validateOtp(int otpNum) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        //Validate the Otp
        if (otpNum >= 0) {
            int serverOTP = otpService.getOtp(email);
            if (serverOTP > 0) {
                if (otpNum == serverOTP) {
                    otpService.clearOTP(email);
                    Optional<User> existingUser = userRepository.findUserByEmail(email);
                    return new ApiResponse<>("success", LocalDateTime.now(), new PrincipalDto(existingUser.get().getId(),
                            existingUser.get().getFirstName(), existingUser.get().getLastName(), existingUser.get().getEmail(), existingUser.get().getRole(),
                            existingUser.get().getPhoneNo()));
                } else {
                    throw new InvalidOTPException("INVALID OTP");
                }
            } else {
                throw new InvalidOTPException("INVALID OTP");
            }
        } else {
            throw new InvalidOTPException("INVALID OTP");
        }
    }

}

