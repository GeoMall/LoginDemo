package com.deltaServices.logindemo.logindemo.Registration;

import com.deltaServices.logindemo.logindemo.Registration.Email.EmailSender;
import com.deltaServices.logindemo.logindemo.Registration.Token.Token;
import com.deltaServices.logindemo.logindemo.Registration.Token.TokenService;
import com.deltaServices.logindemo.logindemo.User.User;
import com.deltaServices.logindemo.logindemo.User.UserService;
import com.deltaServices.logindemo.logindemo.User.userStatus;
import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationService{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("(?:[a-z0-9!#$%&'*+=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", Pattern.CASE_INSENSITIVE);
    private final UserService userService;
    private final TokenService tokenService;
    private final EmailSender emailSender;

    public String register(RegistrationUserDetails registrationUserDetails){

        //Field input validations
        Predicate<String> emptyFieldValidator = (s) -> s != null || !s.isBlank();
        Predicate<String> emailValidator = (email) -> {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return matcher.find();
        };

        if(!emailValidator.test(registrationUserDetails.getEmail())){
            throw new IllegalStateException("The email: " + registrationUserDetails.getEmail() + " is an invalid email.");
        }

        if(!emptyFieldValidator.test(registrationUserDetails.getName())){
            throw new IllegalStateException("The First name: " + registrationUserDetails.getName() + " is empty.");
        }
        else if( !emptyFieldValidator.test(registrationUserDetails.getSurname())){
            throw new IllegalStateException("The surname: " + registrationUserDetails.getSurname() + " is empty.");
        }
        else if( !emptyFieldValidator.test(registrationUserDetails.getPassword())){
            throw new IllegalStateException("The password: " + registrationUserDetails.getPassword() + " is empty.");
        }

        //TODO: Check if user already exists
        //TODO: AND IF EXISTS and email not confirmed, re send confirmation email


        //Saving user and generating token
        String token = userService.signUp(
                new User
                        (
                                registrationUserDetails.getEmail(),
                                registrationUserDetails.getName(),
                                registrationUserDetails.getSurname(),
                                registrationUserDetails.getPassword(),
                                userStatus.User,
                                false,
                                false,
                                false
                        )
        );

        //sending confirmation email
        String url = "http://localhost:8080/loginApi/register/confirmToken?token="+token;
        emailSender.sendEmail(registrationUserDetails.getEmail(),
                buildEmail(registrationUserDetails.getName(),url));

        return token;
    }

    @Transactional
    public String confirmToken(String token){

        Token searchToken = tokenService.findToken(token)
                .orElseThrow( () -> new IllegalStateException("Token could not be found"));

        //Check if token is already confirmed
        if(searchToken.getTokenConfirmedAt() != null){
            throw new IllegalStateException("Token Already Confirmed");
        }

        //check if token is expired
        if(searchToken.getTokenExpireAt().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Token Has Expired");
        }

        //update token confirmed at
        tokenService.updateTokenConfirmationAt(searchToken.getToken(), LocalDateTime.now());

        //Since token is valid and confirmed, the user can be enabled
        userService.enableUser(searchToken.getUser().getEmail());
        return "Token Confirmed";
    }



    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> " +
                "              <form name=\"submitForm\" method=\"POST\" action=\"" + link + "\"><button href=\"javascript:document.submitForm.submit()\">Activate Now</button> </form>" +
                "            </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
