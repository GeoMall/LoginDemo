package com.deltaServices.logindemo.logindemo.User;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
            name= "UserID_Sequence",
            sequenceName = "UserID_Sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long userId;

    private String email;
    private String name;
    private String surname;
    private String password;

    @Enumerated(EnumType.STRING)
    private userStatus status;

    private boolean locked = false;
    private boolean expired = false;
    private boolean enabled = false;

    public User(String email, String name, String surname, String password, userStatus status, boolean locked, boolean expired, boolean enabled) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = status;
        this.locked = locked;
        this.expired = expired;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(status.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
