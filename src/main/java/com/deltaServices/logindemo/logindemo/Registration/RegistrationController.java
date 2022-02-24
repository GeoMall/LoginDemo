package com.deltaServices.logindemo.logindemo.Registration;

import com.deltaServices.logindemo.logindemo.User.User;
import com.deltaServices.logindemo.logindemo.User.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.util.List;

@RestController
@RequestMapping("loginApi/register")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;
    private UserService userService;

    @PostMapping(path="finishRegister")
    public ModelAndView register(@RequestParam String email,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam String password,
                                 @RequestParam String role,
                                 Model model){
        String token;
        RegistrationUserDetails registrationUserDetails = new RegistrationUserDetails(email, name, surname, password, role);

        try{
            token = registrationService.register(registrationUserDetails);
        }catch(Exception ex)
        {
            return new ModelAndView("redirect:/errorPage?errorMessage= " + ex.getMessage());
        }


        return new ModelAndView("redirect:/pageSuccess");
    }

    @GetMapping(path = "getAll")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(path = "confirmToken")
    public String confirmToken(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
