package com.Sysserve.emanifest.serviceImpl;

import com.Sysserve.emanifest.dto.EmailDetailsRequest;
import com.Sysserve.emanifest.model.AccountVerificationEmailContext;
import com.Sysserve.emanifest.service.EmailService;
import com.Sysserve.emanifest.utils.EmailBody;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetailsRequest details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully";
        }catch (Exception e){
            return "Error while sending mail";
        }
    }

    @Override
    public String buildVerificationEmail(String name, String link)  {
        return EmailBody.emailVerificationBody(name,link);
    }

    @Override
    public String sendSimpleAuthMail(AccountVerificationEmailContext details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully";
        }catch (Exception e){
            return "Error while sending mail";
        }
    }

}

