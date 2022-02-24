package com.deltaServices.logindemo.logindemo.Login;

import com.deltaServices.logindemo.logindemo.User.User;
import com.deltaServices.logindemo.logindemo.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class loginController {

    private final UserService userService;
    public List<User> userList;

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/homePage")
    public String homePage(Model model){
        return "homePage";
    }

    @GetMapping("/signup")
    public String signupPage(Model model){
        //model.addAttribute("tokenGenerated","notGenerated");
        return "registerPage";
    }

    @GetMapping("/pageSuccess")
    public ModelAndView signupSuccessPage(Model model){
        //model.addAttribute("tokenGenerated","notGenerated");
        return new ModelAndView("registerSuccessPage");
    }

    @GetMapping("/userList")
    public String userList(Model model){

        model.addAttribute("userList", userService.getAllUsers());

        //TODO: CREATE AN ADMIN USER AT STARTUP
        return "userList";
    }

}
