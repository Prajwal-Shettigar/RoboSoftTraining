package com.prajwal.securityjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("authentication..");
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        System.out.println("authorization..");
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/Admin").hasRole("ADMIN")
                .antMatchers("/User").hasAnyRole("ADMIN","USER")
                .antMatchers("/").permitAll()
                .and().formLogin()
                .and().httpBasic();

    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        System.out.println("password encoder");
        return NoOpPasswordEncoder.getInstance();
    }

}
