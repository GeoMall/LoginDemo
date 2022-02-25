package com.deltaServices.logindemo.logindemo.Login;

import com.deltaServices.logindemo.logindemo.User.User;
import com.deltaServices.logindemo.logindemo.User.UserService;
import com.deltaServices.logindemo.logindemo.User.userStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class loginController {

    //Navigation controller

    private final UserService userService;
    public List<User> userList;

    //login page navigation
    @GetMapping("/login")
    public String loginPage(Model model) {

        //checking if user is already authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        //if user is already authenticated do not let page navigation to login page
        return "redirect:/homePage";
    }

    //home page navigation
    @GetMapping("/homePage")
    public String homePage(Model model){
        return "homePage";
    }

    //sign up page navigation
    @GetMapping("/signup")
    public String signupPage(Model model){
        List<String> roleList = new ArrayList<>();
        roleList.add(userStatus.ROLE_User.toString());
        roleList.add(userStatus.ROLE_Admin.toString());

        model.addAttribute("roleList",roleList);
        return "registerPage";
    }

    //error page navigation
    @GetMapping("/errorPage")
    public String errorPage(@RequestParam String errorMessage, Model model){
        model.addAttribute("errormsg", errorMessage);
        return "errorPage";
    }

    //successful signup page navigation
    @GetMapping("/pageSuccess")
    public ModelAndView signupSuccessPage(Model model){
        return new ModelAndView("registerSuccessPage");
    }

    //user list page navigation
    @GetMapping("/userList")
    public ModelAndView userList(Model model){
        //try to retrieve userlist
        try{
            model.addAttribute("userList", userService.getAllUsers());
        } catch(Exception ex)
        {
            //redirect to error page in case of error
            return new ModelAndView("redirect:/errorPage?errorMessage= " + ex.getMessage());
        }

        //return user list page
        return new ModelAndView("userList");
    }

}
