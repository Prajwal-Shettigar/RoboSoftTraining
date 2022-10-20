package com.prajwal.twitter.service;

import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.UserProfile;

import java.math.BigInteger;
import java.util.List;

public interface TwitterService {

    User registerUser(User user);

    boolean loginAUser(User user);

    User getUserById(String userId);

    Tweet getTweetById(BigInteger tweetId);

    byte[] getProfilePictureById(String userId);

    List<User> getTopAccounts(int limit);

}
