package com.Sysserve.emanifest.controllers;

import com.Sysserve.emanifest.dto.UserDto;
import com.Sysserve.emanifest.response.ApiResponse;
import com.Sysserve.emanifest.security.CurrentUser;
import com.Sysserve.emanifest.security.CustomUserDetails;
import com.Sysserve.emanifest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/newUser")
    public ResponseEntity<?> createNewUser(@RequestBody UserDto dto){
        return new ResponseEntity<>(userService.createUser(dto), CREATED);
    }

@GetMapping("/searchByEmail")
    public ResponseEntity<?> searchByEmail(@RequestParam String email) {
    log.info("success");
    return new ResponseEntity<>(userService.getUserByEmail(email), OK);
}

    @GetMapping("/searchByFirstName")
    public ResponseEntity<?> searchByFirstNam(@RequestParam String firstName) {
        log.info("success");
        return new ResponseEntity<>(userService.getUserByFirstName(firstName), OK);
    }

    @GetMapping("/searchByLastName")
    public ResponseEntity<?> searchByLastName(@RequestParam String lastName) {
        log.info("success");
        return new ResponseEntity<>(userService.getUserByLastName(lastName), OK);
    }

    @GetMapping("/viewAllUsers")
    public ResponseEntity<?> viewAllUsers(){
        return new ResponseEntity<>(userService.viewAllUsers(), OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CurrentUser CustomUserDetails currentUser, @RequestHeader("Authorization") String bearToken) {
        return new ResponseEntity<>(userService.logout(currentUser, bearToken), HttpStatus.OK);
    }
}
