package com.Sysserve.emanifest.controllers;

import com.Sysserve.emanifest.dto.EmailDetailsRequest;
import com.Sysserve.emanifest.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetailsRequest details){
        String status = emailService.sendSimpleMail(details);
        return status;
    }
}
