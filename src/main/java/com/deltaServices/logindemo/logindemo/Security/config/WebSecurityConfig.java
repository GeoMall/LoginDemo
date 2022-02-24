package com.deltaServices.logindemo.logindemo.Security.config;

import com.deltaServices.logindemo.logindemo.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

     /*   http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/h2/**")
                    .permitAll()
                .and()
                    .headers()
                    .frameOptions()
                    .disable();*/


        http
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/h2/**", "/signup/**", "/loginApi/register/**","/pageSuccess/**", "/errorPage").permitAll()
                    .antMatchers("/**").authenticated()
                .and()
                    .headers()
                    .frameOptions()
                    .disable()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/homePage", true)
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/login")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/errorPage?errorMessage=\"Access Denied\"");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoDoaAuthenticationProvider());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public DaoAuthenticationProvider daoDoaAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}
