package com.prajwal.twitter.config;


import com.fasterxml.jackson.databind.annotation.NoClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Bean
    AuthenticationProvider getAuthProvider(){
        DaoAuthenticationProvider provider  = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(this.getPasswordEncoder());
        provider.setUserDetailsService(myUserDetailsService);

        return provider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("Twitter/User/**").hasAnyRole("USER","ADMIN")
                .antMatchers("Twitter/Non/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .formLogin();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
       return new BCryptPasswordEncoder();
    }
}
