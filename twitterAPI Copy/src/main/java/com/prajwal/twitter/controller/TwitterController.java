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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/Twitter")
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    //register
    @PostMapping("/Non/Register")
    public ResponseEntity<String> userRegister(@ModelAttribute RegistrationModel model) throws IOException {


        //if user by that username doesnt exists then
        if(twitterService.getUserById(model.getUserId(),false)==null){


            //create a user
            User user = new User(model.getName(),model.getUserId(), this.encodePassword(model.getPassword()), false,model.getEmail(),model.getPhoneNumber(), BigDecimal.valueOf(0),BigDecimal.valueOf(0),"ROLE_USER");


            //check if profile photo is sent
            if(model.getFile()!=null){
                user.setProfilePhoto(model.getFile().getBytes());
            }

            //register the user
            User returnedUser = twitterService.registerUser(user);

            if(returnedUser!=null){
                return ResponseEntity.accepted().body(user.getUserId());
            }
        }

        //if user by that username is present then
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }




    //list of top accounts to follow
    @GetMapping("/Non/TopAccounts/{limit}")
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

    //home gets the latest tweets by people i follow
    @GetMapping("/User/Home/{limit}")
    public ResponseEntity<List<TweetProfile>> getHome(@PathVariable int limit){
            List<TweetProfile> tweets = twitterService.getTweetsOfFollowing(this.getCurrentUserName(),limit);

            if(tweets!=null)
                return ResponseEntity.ok(tweets);

        return ResponseEntity.noContent().build();

    }

    //post a tweet
    @PostMapping("/User/Tweet")
    public ResponseEntity<HttpStatus> makeATweet(@ModelAttribute TweetModel tweetModel) throws IOException{


        if(twitterService.postATweet(tweetModel,this.getCurrentUserName())!=null)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //get comment on a tweet
    @GetMapping("/Non/Comments/{tweetId}/{limit}")
    public ResponseEntity<List<TweetProfile>> getCommentsOnATweet(@PathVariable BigInteger tweetId,@PathVariable int limit){
        List<TweetProfile> comments = twitterService.getCommentsByTweetId(tweetId,limit);

        if(comments!=null)
            return ResponseEntity.ok(comments);

        return ResponseEntity.noContent().build();
    }



    //follow someone using their user id
    @PutMapping("/User/Follow/{accId}")
    public HttpStatus followAUser(@PathVariable String accId){
        if(twitterService.FollowAUser(this.getCurrentUserName(),accId)){
                return HttpStatus.OK;
        }

        return HttpStatus.EXPECTATION_FAILED;
    }

    //unfollow
    @PutMapping("/User/UnFollow/{accId}")
    public HttpStatus unfollowAUser(@PathVariable String accId){
        if(twitterService.UnFollowAUser(this.getCurrentUserName(),accId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }

    //like a post or comment
    @PutMapping("/User/Like/{tweetId}")
    public HttpStatus likeAPost(@PathVariable BigInteger tweetId){
        if(twitterService.likeAPost(this.getCurrentUserName(),tweetId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //remove like on a  post or comment
    @PutMapping("/User/UnLike/{tweetId}")
    public HttpStatus unLikeAPost(@PathVariable BigInteger tweetId){
        if(twitterService.unLikeAPost(this.getCurrentUserName(),tweetId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }

    //post a comment on a tweet
    @PostMapping("/User/Comment")
    public ResponseEntity<HttpStatus> addAComment(@ModelAttribute TweetModel commentModel) throws IOException{
        if(twitterService.makAComment(commentModel,this.getCurrentUserName())!=null)
            return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }


    //get my followers
    @GetMapping("/User/Followers/{limit}")
    public ResponseEntity<List<UserProfile>> getFollowers(@PathVariable int limit){
        List<UserProfile> myFollowers = twitterService.getMyFollowers(this.getCurrentUserName(),limit);
        if(myFollowers!=null)
            return ResponseEntity.ok(myFollowers);

        return ResponseEntity.noContent().build();
    }


    //get following
    @GetMapping("/User/Following/{limit}")
    public ResponseEntity<List<UserProfile>> getFollowing(@PathVariable int limit){
        List<UserProfile> followingProfiles = twitterService.getFollowingAccounts(this.getCurrentUserName(),limit);

        if(followingProfiles!=null)
            return ResponseEntity.ok(followingProfiles);
        return ResponseEntity.noContent().build();
    }

    //find users by username
    @GetMapping("/Non/Profiles/{name}")
    public ResponseEntity<List<UserProfile>> getUsersByName(@PathVariable String name){
        List<UserProfile> userProfiles = twitterService.getUsersByName(name);

        if(userProfiles!=null)
            return ResponseEntity.ok(userProfiles);


        return ResponseEntity.noContent().build();
    }


    //find tweets by tag
    @GetMapping("/Non/Tweets/{tag}/{limit}")
    public ResponseEntity<List<TweetProfile>> getTweetsByTag(@PathVariable String tag,@PathVariable int limit){
        List<TweetProfile> tweets = twitterService.getTweetsByTag(tag,limit);

        if(tweets!=null)
            return ResponseEntity.ok(tweets);

        return ResponseEntity.noContent().build();
    }


    //update my profile
    @PatchMapping("/User/Profile")
    public HttpStatus updateProfile(@ModelAttribute RegistrationModel model) throws IOException{
        if(twitterService.updateProfile(this.getCurrentUserName(),model))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //update the tweet or comment
    @PatchMapping("/User/Tweet")
    public HttpStatus updateTweet(@ModelAttribute TweetModel tweetModel) throws IOException{
        if(twitterService.updateTweet(this.getCurrentUserName(),tweetModel))
            return HttpStatus.OK;

        return HttpStatus.UNAUTHORIZED;
    }



    //get universal top tweets
    @GetMapping("/Non/Tweets/{limit}")
    public ResponseEntity<List<TweetProfile>> getTopTweets(@PathVariable int limit){
        List<TweetProfile> tweets = twitterService.getTopGlobalTweets(limit);

        if(tweets!=null)
            return ResponseEntity.ok(tweets);

        return ResponseEntity.noContent().build();
    }



    //get your own tweets and comments
    @GetMapping("/User/Tweets/{limit}")
    public ResponseEntity<List<TweetProfile>> getMyTweets(@PathVariable int limit){
        List<TweetProfile> tweets = twitterService.getTweetsByUserId(this.getCurrentUserName(),limit);

        if(tweets!=null)
            return ResponseEntity.ok(tweets);


        return ResponseEntity.noContent().build();
    }

    //get profile image  by id if not found return default image
    @GetMapping("/Non/ProfilePicture/{userId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String userId) throws IOException{

        //downloading from database
        byte[] profilePic = twitterService.getProfilePictureById(userId);

        if(profilePic==null)
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + userId+".png" + "\"").body(new ByteArrayResource(new FileInputStream("C:\\Users\\Prajwal\\Documents\\Prajwal\\twitterAPI\\src\\main\\resources\\static\\profile-picture-default.png").readAllBytes()));


        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + userId+".png" + "\"").body(new ByteArrayResource(profilePic));
    }

    //view self profile
    @GetMapping("/User/Profile")
    public ResponseEntity<UserProfile> getMyProfile(){
        return ResponseEntity.ok(twitterService.getUserById(this.getCurrentUserName(),true));
    }

    //delete a profile
    @DeleteMapping("/User/Profile")
    public HttpStatus deleteProfile(){
        twitterService.deleteMyProfile(this.getCurrentUserName());

        return HttpStatus.OK;
    }


    //delete a comment or a tweet
    @DeleteMapping("/User/{tweetId}")
    public HttpStatus deleteTweetOrComment(@PathVariable BigInteger tweetId){
        if(twitterService.deleteATweet(this.getCurrentUserName(),tweetId))
            return HttpStatus.OK;


        return HttpStatus.EXPECTATION_FAILED;
    }





    //get the tweet post media  based on the tweet id
    @GetMapping("/Non/Post/{tweetId}")
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
    @GetMapping("/Non/Profile/{accId}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable String accId){
        UserProfile userProfile = twitterService.getUserById(accId,false);
        if (userProfile != null)
            return ResponseEntity.ok(userProfile);

        return ResponseEntity.noContent().build();
    }


    public String getCurrentUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }


}
