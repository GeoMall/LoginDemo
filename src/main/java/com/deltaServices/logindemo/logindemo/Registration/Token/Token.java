package com.deltaServices.logindemo.logindemo.Registration.Token;

import com.deltaServices.logindemo.logindemo.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.annotation.SuppressAjWarnings;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @SequenceGenerator(
            name= "Token_Sequence",
            sequenceName = "Token_Sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime tokenCreationAt;

    @Column(nullable = false)
    private LocalDateTime tokenExpireAt;
    private LocalDateTime tokenConfirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public Token(String token, LocalDateTime tokenCreationAt, LocalDateTime tokenExpireAt, User user) {
        this.token = token;
        this.tokenCreationAt = tokenCreationAt;
        this.tokenExpireAt = tokenExpireAt;
        this.user = user;
    }
}
