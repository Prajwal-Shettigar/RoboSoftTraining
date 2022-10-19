package com.prajwal.twitter.service;

import com.prajwal.twitter.entity.Users;
import com.prajwal.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;


@Service
public class TwitterServiceImpl implements TwitterService{

    String query;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    //register a user
    @Override
    public Users registerUser(Users users) {

        try{
            return userRepository.save(users);
        }catch(Exception sqlIntegrityConstraintViolationException){
            System.out.println(sqlIntegrityConstraintViolationException.getMessage());
        }
        return null;
    }

    @Override
    public Users getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
