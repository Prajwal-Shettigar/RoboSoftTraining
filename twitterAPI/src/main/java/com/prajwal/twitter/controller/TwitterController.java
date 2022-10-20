package com.prajwal.twitter.controller;


import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.RegistrationModel;
import com.prajwal.twitter.model.TweetModel;
import com.prajwal.twitter.model.TweetProfile;
import com.prajwal.twitter.model.UserProfile;
import com.prajwal.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/Twitter")
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    private int sessionId;
    private String userId;

    //register
    @PostMapping("/Register")
    public ResponseEntity<Integer> userRegister(@ModelAttribute RegistrationModel model) throws IOException {


        //if user by that username doesnt exists then
        if(twitterService.getUserById(model.getUserId())==null){


            //create a user
            User user = new User(model.getName(),model.getUserId(), model.getPassword(), false,model.getEmail(),model.getPhoneNumber());


            //check if profile photo is sent
            if(!model.getFile().isEmpty()){
                user.setProfilePhoto(model.getFile().getBytes());
            }

            //register the user
            User returnedUser = twitterService.registerUser(user);

            if(returnedUser!=null){

                //store username and session id
                this.userId = returnedUser.getUserId();

                this.sessionId = this.getSessionId();


                return ResponseEntity.accepted().body(sessionId);
            }
        }

        //if user by that username is present then
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }



    //login
    @GetMapping("/Login")
    public ResponseEntity<Integer> userLogin(@RequestBody User user){

        //if username and password are provided and it matches with one at database then return ok status with session id
        if((user.getUserId()!=null)&&(user.getPassword()!=null)){
            if(twitterService.loginAUser(user)){
                this.sessionId = this.getSessionId();
                this.userId = user.getUserId();
                return ResponseEntity.ok(sessionId);
            }
        }

        //otherwise return unauthorized status
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //list of top accounts to follow
    @GetMapping("/TopAccounts/{limit}")
    public ResponseEntity<List<UserProfile>> getTopAccounts(@PathVariable int limit){
        List<User> topUsers = twitterService.getTopAccounts(limit);

        if(topUsers.size()>1){
            List<UserProfile> userProfiles = new ArrayList<>();

            for(User user:topUsers){
                String profileURL = twitterService.profileURLBuilder(user.getUserId());

                userProfiles.add(new UserProfile(user.getName(),user.getUserId(),user.isVerified(),profileURL,user.getFollowingCount(),user.getFollowersCount(),user.getAbout()));
            }

            return ResponseEntity.ok(userProfiles);
        }

        return ResponseEntity.noContent().build();
    }

    //home

    //post a tweet
    @PostMapping("{sid}/Tweet")
    public ResponseEntity<HttpStatus> makeATweet(@PathVariable int sid,@ModelAttribute TweetModel tweetModel) throws IOException{

        //if verified user then
        if(checkSessionId(sid)){
            if(twitterService.postATweet(tweetModel,userId)!=null)
                return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //get comment on a tweet
    @GetMapping("/Comments/{tweetId}/{limit}")
    public ResponseEntity<List<TweetProfile>> getCommentsOnATweet(@PathVariable BigInteger tweetId,@PathVariable int limit){
        List<TweetProfile> comments = twitterService.getCommentsByTweetId(tweetId,limit);

        if(comments!=null)
            return ResponseEntity.ok(comments);

        return ResponseEntity.noContent().build();
    }



    //follow

    //unfollow

    //like a post

    //post a comment on a tweet
    @PostMapping("{sid}/Comment")
    public ResponseEntity<HttpStatus> addAComment(@PathVariable int sid, @ModelAttribute TweetModel commentModel) throws IOException{
        if(checkSessionId(sid)){
            if(twitterService.makAComment(commentModel,userId)!=null){
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //get followers

    //find users by username


    //find tweets by tag
    @GetMapping("/Tweets/{tag}/{limit}")
    public ResponseEntity<TweetProfile> getTweetsByTag(@PathVariable String tag,@PathVariable int limit){
        List<TweetProfile> tweets = twitterService.getTweetsByTag(tag,limit);

        if(tweets!=null)
            return ResponseEntity.ok(tweets);

        return ResponseEntity.noContent().build();
    }

    //update the about

    //update the profile pic



    //get your own tweets and comments
    @GetMapping("/{sid}/Tweets/{limit}")
    public ResponseEntity<List<TweetProfile>> getMyTweets(@PathVariable int sid,@PathVariable int limit){
        if(checkSessionId(sid)){
            List<TweetProfile> tweets = twitterService.getTweetsByUserId(userId,limit);

            if(tweets!=null)
                return ResponseEntity.ok(tweets);
        }

        return ResponseEntity.noContent().build();
    }

    //get profile image  by id if not found return default image
    @GetMapping("/Profile/{userId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String userId) throws IOException{

        //downloading from database
        byte[] profilePic = twitterService.getProfilePictureById(userId);

        if(profilePic==null)
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + userId+".png" + "\"").body(new ByteArrayResource(new FileInputStream("C:\\Users\\Prajwal\\Documents\\Prajwal\\twitterAPI\\src\\main\\resources\\static\\profile-picture-default.png").readAllBytes()));


        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + userId+".png" + "\"").body(new ByteArrayResource(profilePic));
    }




    //get the tweet post media  based on the tweet id
    @GetMapping("/Post/{tweetId}")
    public ResponseEntity<Resource> getPostMediaById(@PathVariable BigInteger tweetId){
        Tweet tweet = twitterService.getTweetById(tweetId);

        if(tweet!=null){
            if(tweet.getMedia()!=null){
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(tweet.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + tweetId+tweet.getFileName() + "\"").body(new ByteArrayResource(tweet.getMedia()));
            }
        }

        return ResponseEntity.noContent().build();
    }

    //find user profile by user id
    @GetMapping("/User/{id}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable String id){
        //get the user from service by id
        User user = twitterService.getUserById(id);

        //if user exists
        if(user !=null){
            //create a url; for profile pic
            String profileURL = twitterService.profileURLBuilder(user.getUserId());

            //create a user profile
            UserProfile userProfile = new UserProfile(user.getName(),user.getUserId(),user.isVerified(),profileURL,user.getFollowingCount(),user.getFollowersCount(),user.getAbout());

            return ResponseEntity.ok(userProfile);
        }


        return ResponseEntity.noContent().build();
    }


    //get session id
    private int getSessionId(){
        return new Random().nextInt(1000,20000);
    }

    //check session id
    private boolean checkSessionId(int sessionId){
        return sessionId == this.sessionId;
    }



}
