package com.prajwal.manytomany.service;


import com.prajwal.manytomany.entity.User;
import com.prajwal.manytomany.repository.ComputerRepository;
import com.prajwal.manytomany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CyberService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ComputerRepository computerRepository;


    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
