package com.Sysserve.emanifest.serviceImpl;

import com.Sysserve.emanifest.model.ConfirmationToken;
import com.Sysserve.emanifest.repository.ConfirmationTokenRepository;
import com.Sysserve.emanifest.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@Service
@Getter
@Setter
@AllArgsConstructor

public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(6);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Value("${jdj.confirmation.token.validity}")
    private int tokenValidityInSeconds;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public ConfirmationToken createConfirmationToken() {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()));
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(tokenValue);
        confirmationToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveConfirmationToken(confirmationToken);
        return confirmationToken;


    }
    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }
    @Override
    public ConfirmationToken findByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public void removeToken(ConfirmationToken token) {
        confirmationTokenRepository.delete(token);
    }
}
