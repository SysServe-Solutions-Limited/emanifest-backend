package com.Sysserve.emanifest.dto;

import com.Sysserve.emanifest.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrincipalDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String telephone;

    public PrincipalDto(Long id, String firstName, String lastName, String email, Role role, String telephone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.telephone = telephone;
    }
}
