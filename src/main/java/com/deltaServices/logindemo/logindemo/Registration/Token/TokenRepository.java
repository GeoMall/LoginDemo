package com.deltaServices.logindemo.logindemo.Registration.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("Update Token"+
            " Set tokenConfirmedAt = ?2 " +
            " Where token = ?1 "
            )
    int updateTokenConfirmedAt(String token, LocalDateTime confirmedAt);
}
