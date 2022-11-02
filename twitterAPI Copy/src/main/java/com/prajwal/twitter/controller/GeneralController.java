package com.prajwal.twitter.controller;

import com.prajwal.twitter.config.AllUserPermission;
import com.prajwal.twitter.config.MyUserDetailsService;
import com.prajwal.twitter.config.TokenManager;
import com.prajwal.twitter.customeExceptions.DuplicateUsername;
import com.prajwal.twitter.customeExceptions.NoContentToDisplay;
import com.prajwal.twitter.customeExceptions.UserDoesNotExists;
import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.JwtResponseModel;
import com.prajwal.twitter.model.RegistrationModel;
import com.prajwal.twitter.model.TweetProfile;
import com.prajwal.twitter.model.UserProfile;
import com.prajwal.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Twitter/Non")
public class GeneralController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TwitterService twitterService;


    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenManager tokenManager;

    //register
    @PostMapping("/Register")
    public ResponseEntity<String> userRegister(@ModelAttribute RegistrationModel model) throws IOException, DuplicateUsername {


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
        throw new DuplicateUsername();
    }

//    @Secured({"ROLE_USER","ROLE_ADMIN"})

//    @RolesAllowed("ROLE_USER")

//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")

//    @PreAuthorize("#loginModel.getUserId()=='pj878'")

//    @PostAuthorize("#loginModel.getUserId()=='pj878'")

    @AllUserPermission
    @PostMapping("/Login")
    public ResponseEntity<JwtResponseModel> userLogin(@RequestBody RegistrationModel loginModel){

        System.out.println("login attempted..");


        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getUserId(),loginModel.getPassword()));


        UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginModel.getUserId());

        String token = tokenManager.generateJwtToken(userDetails);

        JwtResponseModel responseModel  = new JwtResponseModel();
        responseModel.setJwtToken(token);

        return ResponseEntity.ok(responseModel);
    }

    //list of top accounts to follow
    @GetMapping("/TopAccounts/{limit}")
    public ResponseEntity<List<UserProfile>> getTopAccounts(@PathVariable int limit) throws NoContentToDisplay {
        List<User> topUsers = twitterService.getTopAccounts(limit);

        if(topUsers.size()>1){
            List<UserProfile> userProfiles = new ArrayList<>();

            for(User user:topUsers){
                String profileURL = twitterService.profileURLBuilder(user.getUserId());

                userProfiles.add(new UserProfile(user.getName(),user.getUserId(),user.isVerified(),profileURL,user.getFollowingCount(),user.getFollowersCount(),user.getAbout()));
            }

            return ResponseEntity.ok(userProfiles);
        }

        throw new NoContentToDisplay();
    }


    //get comment on a tweet
    @GetMapping("/Comments/{tweetId}/{limit}")
    public ResponseEntity<List<TweetProfile>> getCommentsOnATweet(@PathVariable BigInteger tweetId, @PathVariable int limit) throws NoContentToDisplay {
        List<TweetProfile> comments = twitterService.getCommentsByTweetId(tweetId,limit);

        if(comments!=null)
            return ResponseEntity.ok(comments);

        throw new NoContentToDisplay();
    }

    //find users by username
    @GetMapping("/Profiles/{name}")
    public ResponseEntity<List<UserProfile>> getUsersByName(@PathVariable String name) throws UserDoesNotExists {
        List<UserProfile> userProfiles = twitterService.getUsersByName(name);

        if(userProfiles!=null)
            return ResponseEntity.ok(userProfiles);


        throw new UserDoesNotExists();

    }


    //find tweets by tag
    @GetMapping("/Tweets/{tag}/{limit}")
    public ResponseEntity<List<TweetProfile>> getTweetsByTag(@PathVariable String tag,@PathVariable int limit) throws NoContentToDisplay {
        List<TweetProfile> tweets = twitterService.getTweetsByTag(tag,limit);

        if(tweets!=null)
            return ResponseEntity.ok(tweets);

        throw new NoContentToDisplay();
    }


    //get universal top tweets
    @GetMapping("/Tweets/{limit}")
    public ResponseEntity<List<TweetProfile>> getTopTweets(@PathVariable int limit) throws NoContentToDisplay {
        List<TweetProfile> tweets = twitterService.getTopGlobalTweets(limit);

        if(tweets!=null)
            return ResponseEntity.ok(tweets);

        throw new NoContentToDisplay();
    }

    //get the tweet post media  based on the tweet id
    @GetMapping("/Post/{tweetId}")
    public ResponseEntity<Resource> getPostMediaById(@PathVariable BigInteger tweetId) throws NoContentToDisplay {
        Tweet tweet = twitterService.getTweetById(tweetId);

        if(tweet!=null){
            if(tweet.getMedia()!=null){
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(tweet.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + tweetId+tweet.getFileName() + "\"").body(new ByteArrayResource(tweet.getMedia()));
            }
        }

        throw new NoContentToDisplay();
    }

    //find user profile by user id
    @GetMapping("/Profile/{accId}")
    public ResponseEntity<UserProfile> getUserById(@PathVariable String accId) throws UserDoesNotExists {
        UserProfile userProfile = twitterService.getUserById(accId,false);
        if (userProfile != null)
            return ResponseEntity.ok(userProfile);

        throw new UserDoesNotExists();
    }

    //get profile image  by id if not found return default image
    @GetMapping("/ProfilePicture/{userId}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String userId) throws IOException{

        //downloading from database
        byte[] profilePic = twitterService.getProfilePictureById(userId);

        if(profilePic==null)
            return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + userId+".png" + "\"").body(new ByteArrayResource(new FileInputStream("C:\\Users\\Prajwal\\Documents\\Prajwal\\twitterAPI\\src\\main\\resources\\static\\profile-picture-default.png").readAllBytes()));


        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header(HttpHeaders.CONTENT_DISPOSITION,"filename=\"" + userId+".png" + "\"").body(new ByteArrayResource(profilePic));
    }

    public String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }
}
