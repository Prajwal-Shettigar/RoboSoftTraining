package com.prajwal.twitter.service;

import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.RegistrationModel;
import com.prajwal.twitter.model.TweetModel;
import com.prajwal.twitter.model.TweetProfile;
import com.prajwal.twitter.model.UserProfile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public interface TwitterService {

    User registerUser(User user);

    boolean loginAUser(User user);

    UserProfile getUserById(String userId,boolean self);

    Tweet getTweetById(BigInteger tweetId);

    byte[] getProfilePictureById(String userId);

    List<User> getTopAccounts(int limit);

    List<TweetProfile> getTweetsByUserId(String userId,int limit);

    Tweet postATweet(TweetModel tweetModel,String userId) throws IOException;

    Tweet makAComment(TweetModel commentModel,String userId) throws IOException;

    String profileURLBuilder(String userId);

    String postURLBuilder(BigInteger tweetId);

    List<TweetProfile> getCommentsByTweetId(BigInteger tweetId,int limit);

    List<TweetProfile> getTweetsByTag(String tag,int limit);

    List<TweetProfile> getTweetsOfFollowing(String userId,int limit);

    boolean FollowAUser(String userId,String accId);

    boolean UnFollowAUser(String userId,String accId);

    boolean likeAPost(String userId,BigInteger tweetId);

    boolean unLikeAPost(String userId,BigInteger tweetId);

    List<UserProfile> getMyFollowers(String userId,int limit);

    boolean checkIfFollowingAnAccount(String userId,String accId);

    List<UserProfile> getUsersByName(String name);

    List<UserProfile> getFollowingAccounts(String userId,int limit);

    List<TweetProfile> getTopGlobalTweets(int limit);

    boolean updateProfile(String userId, RegistrationModel model) throws IOException;

    boolean updateTweet(String userId,TweetModel tweetModel) throws IOException;

}
