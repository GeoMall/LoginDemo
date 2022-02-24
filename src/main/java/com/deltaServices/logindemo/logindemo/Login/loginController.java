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

    private final UserService userService;
    public List<User> userList;

    @GetMapping("/login")
    public String loginPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/homePage";
    }

    @GetMapping("/homePage")
    public String homePage(Model model){
        return "homePage";
    }

    @GetMapping("/signup")
    public String signupPage(Model model){
        List<String> roleList = new ArrayList<>();
        roleList.add(userStatus.ROLE_User.toString());
        roleList.add(userStatus.ROLE_Admin.toString());

        model.addAttribute("roleList",roleList);
        return "registerPage";
    }


    @GetMapping("/errorPage")
    public String errorPage(@RequestParam String errorMessage, Model model){
        model.addAttribute("errormsg", errorMessage);
        return "errorPage";
    }

    @GetMapping("/pageSuccess")
    public ModelAndView signupSuccessPage(Model model){
        //model.addAttribute("tokenGenerated","notGenerated");
        return new ModelAndView("registerSuccessPage");
    }

    @GetMapping("/userList")
    public String userList(Model model){
        model.addAttribute("userList", userService.getAllUsers());
        return "userList";
    }

}
