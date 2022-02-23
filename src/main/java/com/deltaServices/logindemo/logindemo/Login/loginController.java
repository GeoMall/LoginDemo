package com.deltaServices.logindemo.logindemo.Login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class loginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/homePage")
    public String homePage(Model model){

        //TODO: Get CURRENT LOGGED IN USER
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)  SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        //TODO: CHECK PRIVILEGES to check if user is admin
        if(authorities.iterator().next().getAuthority().equals("ADMIN"))
        {

        }
        return "homePage";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        return "userList";
    }

}
