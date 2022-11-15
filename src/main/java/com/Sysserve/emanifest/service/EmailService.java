package com.Sysserve.emanifest.service;
import com.Sysserve.emanifest.dto.EmailDetailsRequest;
import com.Sysserve.emanifest.model.AccountVerificationEmailContext;

public interface EmailService {

    String sendSimpleMail(EmailDetailsRequest details);

    String buildVerificationEmail(String name, String link);

    String sendSimpleAuthMail(AccountVerificationEmailContext details);
}
