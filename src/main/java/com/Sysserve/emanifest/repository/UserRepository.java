package com.Sysserve.emanifest.repository;

import com.Sysserve.emanifest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
 Optional<User> findUserByEmail(String email);
 Optional<User> findUserByFirstName(String firstName);
 Optional<User> findUserByLastName(String lastName);
}
