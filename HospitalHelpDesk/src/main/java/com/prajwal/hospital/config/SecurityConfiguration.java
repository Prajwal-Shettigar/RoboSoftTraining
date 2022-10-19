package com.prajwal.hospital.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {



    //configuring the user for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("patient").password("patient").roles("PATIENT").
                and().
                withUser("doctor").password("doctor").roles("DOCTOR").
                and().
                withUser("admin").password("admin").roles("ADMIN");
    }

    //configuring the authorization


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/Hospital/Admin/**").hasRole("ADMIN").
                antMatchers("/Hospital/Doctor/**").hasAnyRole("DOCTOR","ADMIN").
                antMatchers("/Hospital/HelpDesk/**").hasAnyRole("PATIENT","ADMIN").
                antMatchers("/User/**").permitAll().and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
