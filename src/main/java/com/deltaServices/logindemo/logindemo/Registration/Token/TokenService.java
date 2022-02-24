package com.deltaServices.logindemo.logindemo.Registration.Token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveTokenConfirmation(Token token){
        tokenRepository.save(token);
    }

    public Optional<Token> findToken(String token){
        return tokenRepository.findByToken(token);
    }

    public void updateTokenConfirmationAt(String token, LocalDateTime confirmedAt){
        tokenRepository.updateTokenConfirmedAt(token,confirmedAt);
    }

}
