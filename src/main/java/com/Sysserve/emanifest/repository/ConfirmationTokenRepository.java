package com.Sysserve.emanifest.repository;

import com.Sysserve.emanifest.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    Long RemoveByToken(String token);

    ConfirmationToken findByToken(String token);

}
