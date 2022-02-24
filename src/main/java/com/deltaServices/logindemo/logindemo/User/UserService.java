package com.deltaServices.logindemo.logindemo.User;

import com.deltaServices.logindemo.logindemo.Registration.Token.Token;
import com.deltaServices.logindemo.logindemo.Registration.Token.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " could not be found"));
    }

    public String signUp(User user){
        //check if user exists
        if(userRepo.findByEmail(user.getEmail())
                .isPresent())
        {
            throw new IllegalStateException("Email already in use!");
        }

        String encodedPass = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPass);

        userRepo.save(user);

        //Token Generation
        String tokenGen = UUID.randomUUID().toString();
        Token token = new Token(
                tokenGen,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        tokenService.saveTokenConfirmation(token);

        return tokenGen;
    }

    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    public void enableUser(String email){
        userRepo.enableUser(email);
    }
}
