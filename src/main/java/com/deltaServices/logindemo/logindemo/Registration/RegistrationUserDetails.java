package com.deltaServices.logindemo.logindemo.Registration;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class RegistrationUserDetails {
    private final String email;
    private final String name;
    private final String surname;
    private final String password;
    private final String role;
}
