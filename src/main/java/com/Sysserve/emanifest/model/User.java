package com.Sysserve.emanifest.model;

import com.Sysserve.emanifest.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseClass implements Serializable {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNo;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(columnDefinition = "boolean default false")
    private boolean isAccountVerified;

}
