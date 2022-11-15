package com.Sysserve.emanifest.service;

import com.Sysserve.emanifest.model.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken createConfirmationToken();

    void saveConfirmationToken(ConfirmationToken token);

    ConfirmationToken findByToken(String token);

    void removeToken(ConfirmationToken token);
}
