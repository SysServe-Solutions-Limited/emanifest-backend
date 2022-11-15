package com.Sysserve.emanifest.model;

import lombok.Data;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class AccountVerificationEmailContext extends AbstractEmailContext{
    private String token;
    private String recipient;
    private String msgBody;



    @Override
    public <T> void init(T context){
        //we can do any common configuration setup here
        // like setting up some base URL and context
        User user = (User) context; // we pass the customer information
        put("firstName", user.getFirstName());
        setTemplateLocation("emails/email-verification");
        setSubject("Activate your account");
        setFrom("alabamary95@gmail.com");
        setTo(user.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token){
        final String url= UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }

}
