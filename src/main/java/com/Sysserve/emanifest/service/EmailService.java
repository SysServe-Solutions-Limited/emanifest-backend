package com.Sysserve.emanifest.service;
import com.Sysserve.emanifest.dto.EmailDetailsRequest;

public interface EmailService {

    String sendSimpleMail(EmailDetailsRequest details);

    String buildVerificationEmail(String name, String link);
}
