package com.deltaServices.logindemo.logindemo.Registration;

import com.deltaServices.logindemo.logindemo.User.User;
import com.deltaServices.logindemo.logindemo.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loginApi/register")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;
    private UserService userService;

    @PostMapping
    public String register(@RequestBody RegistrationUserDetails registrationUserDetails){
        return registrationService.register(registrationUserDetails);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @PostMapping(path = "confirmToken")
    public String confirmToken(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
