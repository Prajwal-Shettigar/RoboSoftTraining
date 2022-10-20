package com.prajwal.twitter.service;

import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.UserProfile;
import com.prajwal.twitter.repository.TweetRepository;
import com.prajwal.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;


@Service
public class TwitterServiceImpl implements TwitterService{

    String query;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    //register a user
    @Override
    public User registerUser(User user) {

        try{
            return userRepository.save(user);
        }catch(Exception sqlIntegrityConstraintViolationException){
            System.out.println(sqlIntegrityConstraintViolationException.getMessage());
        }
        return null;
    }


    //login a user
    @Override
    public boolean loginAUser(User user) {

        //get user from database by id
        User returnedUser = this.getUserById(user.getUserId());


        //if user exists and password matches then return true otherwise return false
        if(returnedUser!=null){
            if(user.getPassword().equalsIgnoreCase(returnedUser.getPassword()))
                return true;
        }

        return false;
    }

    //get a user based on id
    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }


    //get profile pic by id
    @Override
    public byte[] getProfilePictureById(String userId) {
        User user =  userRepository.findById(userId).orElse(null);

        if(user!=null){
            if(user.getProfilePhoto()!=null)
                return user.getProfilePhoto();
        }

        return null;
    }


    //get a tweet based on id
    @Override
    public Tweet getTweetById(BigInteger tweetId) {
        return tweetRepository.findById(tweetId).orElse(null);
    }

    @Override
    public List<User> getTopAccounts(int limit) {
       return userRepository.findAllByOrderByFollowersCountDesc(Pageable.ofSize(limit));
    }
}
