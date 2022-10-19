package com.prajwal.securityjpa.config;

import com.prajwal.securityjpa.entity.User;
import com.prajwal.securityjpa.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    UserRepo userRepo;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> user = userRepo.findByName(username);

        user.orElseThrow(()->new UsernameNotFoundException("username not found.."));

        return new MyUserDetails(user.get());
    }
}
