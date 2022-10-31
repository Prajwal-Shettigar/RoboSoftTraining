package com.prajwal.twitter.controller;


import com.prajwal.twitter.config.MyUserDetailsService;
import com.prajwal.twitter.config.TokenManager;
import com.prajwal.twitter.customeExceptions.NoContentToDisplay;
import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.*;
import com.prajwal.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Twitter/User")
public class UserController {

    @Autowired
    TwitterService twitterService;






    //home gets the latest tweets by people i follow
    @GetMapping("/Home/{limit}")
    public ResponseEntity<List<TweetProfile>> getHome(@PathVariable int limit) throws NoContentToDisplay {
            List<TweetProfile> tweets = twitterService.getTweetsOfFollowing(this.getCurrentUserName(),limit);

            if(tweets!=null)
                return ResponseEntity.ok(tweets);

        throw new NoContentToDisplay();

    }

    //post a tweet
    @PostMapping("/Tweet")
    public ResponseEntity<HttpStatus> makeATweet(@ModelAttribute TweetModel tweetModel) throws IOException{


        if(twitterService.postATweet(tweetModel,this.getCurrentUserName())!=null)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }




    //follow someone using their user id
    @PutMapping("/Follow/{accId}")
    public HttpStatus followAUser(@PathVariable String accId){
        if(twitterService.FollowAUser(this.getCurrentUserName(),accId)){
                return HttpStatus.OK;
        }

        return HttpStatus.EXPECTATION_FAILED;
    }

    //unfollow
    @PutMapping("/UnFollow/{accId}")
    public HttpStatus unfollowAUser(@PathVariable String accId){
        if(twitterService.UnFollowAUser(this.getCurrentUserName(),accId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }

    //like a post or comment
    @PutMapping("/Like/{tweetId}")
    public HttpStatus likeAPost(@PathVariable BigInteger tweetId){
        if(twitterService.likeAPost(this.getCurrentUserName(),tweetId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //remove like on a  post or comment
    @PutMapping("/UnLike/{tweetId}")
    public HttpStatus unLikeAPost(@PathVariable BigInteger tweetId){
        if(twitterService.unLikeAPost(this.getCurrentUserName(),tweetId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }

    //post a comment on a tweet
    @PostMapping("/Comment")
    public ResponseEntity<HttpStatus> addAComment(@ModelAttribute TweetModel commentModel) throws IOException{
        if(twitterService.makAComment(commentModel,this.getCurrentUserName())!=null)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }


    //get my followers
    @GetMapping("/Followers/{limit}")
    public ResponseEntity<List<UserProfile>> getFollowers(@PathVariable int limit) throws NoContentToDisplay {
        List<UserProfile> myFollowers = twitterService.getMyFollowers(this.getCurrentUserName(),limit);
        if(myFollowers!=null)
            return ResponseEntity.ok(myFollowers);

        throw new NoContentToDisplay();
    }


    //get following
    @GetMapping("/Following/{limit}")
    public ResponseEntity<List<UserProfile>> getFollowing(@PathVariable int limit) throws NoContentToDisplay {
        List<UserProfile> followingProfiles = twitterService.getFollowingAccounts(this.getCurrentUserName(),limit);

        if(followingProfiles!=null)
            return ResponseEntity.ok(followingProfiles);
        throw new NoContentToDisplay();
    }




    //update my profile
    @PatchMapping("/Profile")
    public HttpStatus updateProfile(@ModelAttribute RegistrationModel model) throws IOException{
        if(twitterService.updateProfile(this.getCurrentUserName(),model))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //update the tweet or comment
    @PatchMapping("/Tweet")
    public HttpStatus updateTweet(@ModelAttribute TweetModel tweetModel) throws IOException{
        if(twitterService.updateTweet(this.getCurrentUserName(),tweetModel))
            return HttpStatus.OK;

        return HttpStatus.UNAUTHORIZED;
    }


    //get your own tweets and comments
    @GetMapping("/Tweets/{limit}")
    public ResponseEntity<List<TweetProfile>> getMyTweets(@PathVariable int limit) throws NoContentToDisplay {
        List<TweetProfile> tweets = twitterService.getTweetsByUserId(this.getCurrentUserName(),limit);

        if(tweets!=null)
            return ResponseEntity.ok(tweets);


        throw new NoContentToDisplay();
    }



    //view self profile
    @GetMapping("/Profile")
    public ResponseEntity<UserProfile> getMyProfile(){
        return ResponseEntity.ok(twitterService.getUserById(this.getCurrentUserName(),true));
    }

    //delete a profile
    @DeleteMapping("/Profile")
    public HttpStatus deleteProfile(){
        twitterService.deleteMyProfile(this.getCurrentUserName());

        return HttpStatus.OK;
    }


    //delete a comment or a tweet
    @DeleteMapping("/{tweetId}")
    public HttpStatus deleteTweetOrComment(@PathVariable BigInteger tweetId){
        if(twitterService.deleteATweet(this.getCurrentUserName(),tweetId))
            return HttpStatus.OK;


        return HttpStatus.EXPECTATION_FAILED;
    }




    public String getCurrentUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }




}
