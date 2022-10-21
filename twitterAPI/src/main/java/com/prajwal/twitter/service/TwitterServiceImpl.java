package com.prajwal.twitter.service;

import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.*;
import com.prajwal.twitter.repository.TweetRepository;
import com.prajwal.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class TwitterServiceImpl implements TwitterService {

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

        try {
            return userRepository.save(user);
        } catch (Exception sqlIntegrityConstraintViolationException) {
            System.out.println(sqlIntegrityConstraintViolationException.getMessage());
        }
        return null;
    }


    //login a user
    @Override
    public boolean loginAUser(User user) {

        //get user from database by id
        User returnedUser = userRepository.findById(user.getUserId()).orElse(null);


        //if user exists and password matches then return true otherwise return false
        if (returnedUser != null) {
            if (user.getPassword().equalsIgnoreCase(returnedUser.getPassword()))
                return true;
        }

        return false;
    }

    //get a user based on id
    @Override
    public UserProfile getUserById(String userId,boolean self) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            //create a url; for profile pic
            String profileURL = this.profileURLBuilder(user.getUserId());

            //create a user profile
            UserProfile userProfile = new UserProfile(user.getName(), user.getUserId(), user.isVerified(), profileURL, user.getFollowingCount(), user.getFollowersCount(), user.getAbout());

            if(self){
                userProfile.setEmail(user.getEmail());
                userProfile.setPhoneNumber(user.getPhoneNumber());
            }

            return userProfile;
        }

        return null;
    }


    //get profile pic by id
    @Override
    public byte[] getProfilePictureById(String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            if (user.getProfilePhoto() != null)
                return user.getProfilePhoto();
        }

        return null;
    }


    //get a tweet based on  tweet id
    @Override
    public Tweet getTweetById(BigInteger tweetId) {
        return tweetRepository.findById(tweetId).orElse(null);
    }


    //get top followed accounts
    @Override
    public List<User> getTopAccounts(int limit) {
        return userRepository.findAllByOrderByFollowersCountDesc(Pageable.ofSize(limit));
    }

    //get comments on a tweet
    @Override
    public List<TweetProfile> getCommentsByTweetId(BigInteger tweetId, int limit) {
        query = "select comment_id from comments where tweet_id="+tweetId+" order by comment_id desc limit "+limit;

        List<BigInteger> commentIds = jdbcTemplate.queryForList(query, BigInteger.class);

        return this.getTweetsByTweetIds(commentIds);

    }


    @Override
    public List<TweetProfile> getTopGlobalTweets(int limit) {
        query = "select tweet_id from tweets where tweet_id not in (select comment_id from comments)order by tweet_time desc,likes_count desc limit "+limit;

        List<BigInteger> tweetIds = jdbcTemplate.queryForList(query, BigInteger.class);

        return getTweetsByTweetIds(tweetIds);
    }

    //get tweets by people i follow
    @Override
    public List<TweetProfile> getTweetsOfFollowing(String userId, int limit) {

        //get the tweet id of the tweets that were recently made by people i follow
        query = "select tweet_id from tweets t inner join followers f on t.user_id=f.user_id where follower_id='" + userId + "' and tweet_id not in (select comment_id from comments) order by tweet_time desc limit " + limit;
        List<BigInteger> tweetIds = jdbcTemplate.queryForList(query, BigInteger.class);

        return this.getTweetsByTweetIds(tweetIds);
    }

    @Override
    public boolean FollowAUser(String userId, String accId) {

        //add into followers table
        query = "insert into followers values('"+accId+"','"+userId+"')";

        try{
            jdbcTemplate.update(query);
        }catch (Exception exception){
            return false;
        }

        this.updateFollowersCount(accId,1);

        this.updateFollowingCount(userId,1);

        return true;
    }

    @Override
    public boolean unLikeAPost(String userId, BigInteger tweetId) {
        query = "delete from likes where user_id='"+userId+"' and tweet_id="+tweetId;

        if(jdbcTemplate.update(query)<1)
            return false;

        this.changeLikesCountOnPost(tweetId,-1);

        return true;
    }

    @Override
    public List<UserProfile> getMyFollowers(String userId,int limit) {

        //get id of the user who follows u and also your id if he follows u back
        query = "select f.follower_id,b.follower_id from followers f left join followers b on f.user_id=b.follower_id and f.follower_id=b.user_id where f.user_id='"+userId+"' limit "+limit;

        List<Follower> followers = jdbcTemplate.query(query,(resultSet,noOfRows)->{
            Follower follower = new Follower();

            follower.setFollowerId(resultSet.getString(1));
            follower.setUserId(resultSet.getString(2));

            return follower;
        });

        if(followers.size()>0){
            List<UserProfile> userProfiles = new ArrayList<>();

            for(Follower follower:followers){
                UserProfile userProfile = getOtherUserProfileUsingId(follower.getFollowerId());

                userProfile.setFollowing(false);

                //if you are following back set following to true
                if(follower.getUserId()!=null)
                    userProfile.setFollowing(true);


                userProfiles.add(userProfile);
            }

            return userProfiles;

        }

        return null;

    }


    //check if u follow an account
    @Override
    public boolean checkIfFollowingAnAccount(String userId,String accId){
        query = "select user_id from followers where user_id='"+accId+"' and follower_id='"+userId+"'";

        String returnedId = jdbcTemplate.queryForObject(query,String.class);

        if(returnedId!=null)
            return true;

        return false;
    }

    @Override
    public List<UserProfile> getFollowingAccounts(String userId, int limit) {

        //get the user id of users i follow
        query = "select user_id from followers where follower_id='"+userId+"' limit "+limit;

        List<String> followingIds = jdbcTemplate.queryForList(query,String.class);

        if(followingIds!=null){

            List<UserProfile> followingUsers = new ArrayList<>();
            for(String fId:followingIds){
                UserProfile userProfile = this.getOtherUserProfileUsingId(fId);
                userProfile.setFollowing(true);
                followingUsers.add(userProfile);
            }

            return followingUsers;
        }

        return null;

    }

    @Override
    public List<UserProfile> getUsersByName(String name) {

        List<User> userIds = userRepository.findByName(name);

        if(userIds!=null){
            List<UserProfile> userProfiles = new ArrayList<>();

            for(User user:userIds){
                userProfiles.add(this.getUserById(user.getUserId(),false));
            }

            return userProfiles;
        }

        return null;
    }

    //get user profile using id for other user
    public UserProfile getOtherUserProfileUsingId(String userId){
        query = "select name,user_id,verified from users where user_id='"+userId+"'";

        return jdbcTemplate.query(query,(resultSet)->{
            if(!resultSet.next())
                return null;
            UserProfile userProfile = new UserProfile();

            userProfile.setUserName(resultSet.getString(1));
            userProfile.setUserId(resultSet.getString(2));
            userProfile.setVerified(resultSet.getBoolean(3));
            userProfile.setProfileURL(this.profileURLBuilder(userId));

            return userProfile;

        });
    }

    @Override
    public boolean likeAPost(String userId, BigInteger tweetId) {

        //insert into likes table
        query = "insert into likes values('"+userId+"',"+tweetId+")";

        try{
            jdbcTemplate.update(query);
        }catch(Exception exception){
            return false;
        }

        this.changeLikesCountOnPost(tweetId,1);

        return true;
    }


    //change likes count
    public void changeLikesCountOnPost(BigInteger tweetId,int count){

        //get the likes on that tweet
        Tweet tweet= tweetRepository.findById(tweetId).orElse(null);

        BigDecimal likesCount = tweet.getLikesCount();

        //update the likes on that tweet
        query = "update tweets set likes_count="+(likesCount.add(BigDecimal.valueOf(count)))+" where tweet_id="+tweetId;
        jdbcTemplate.update(query);


    }

    //update the number of followers
    public void updateFollowersCount(String userId,int count){
        //get the number of followers of that user
        User user = userRepository.findById(userId).orElse(null);
        BigDecimal followerCount = user.getFollowersCount();

        //increase the number of followers of that user by one
        query = "update users set followers_count="+followerCount.add(BigDecimal.valueOf(count))+" where user_id='"+userId+"'";
        jdbcTemplate.update(query);
    }

    //update the number of following
    public void updateFollowingCount(String userId,int count){
        //get the number of people i follow
        User user  = userRepository.findById(userId).orElse(null);
        BigDecimal followingCount = user.getFollowingCount();

        //increase the number of people i follow by one
        query = "update users set following_count="+followingCount.add(BigDecimal.valueOf(count))+" where user_id='"+userId+"'";
        jdbcTemplate.update(query);
    }

    @Override
    public List<TweetProfile> getTweetsByTag(String tag, int limit) {
        query = "select tweet_id from tags where tag='"+tag+"' and tweet_id not in (select comment_id from comments) order by tweet_id desc limit "+limit;
        List<BigInteger> tweetIds = jdbcTemplate.queryForList(query, BigInteger.class);

        return this.getTweetsByTweetIds(tweetIds);
    }

    //get tweets based on user id
    @Override
    public List<TweetProfile> getTweetsByUserId(String userId,int limit) {
        query = "select tweet_id from tweets where user_id='"+userId+"' order by tweet_time desc limit "+limit;

        List<BigInteger> tweetsByUser = jdbcTemplate.queryForList(query, BigInteger.class);

        return this.getTweetsByTweetIds(tweetsByUser);

    }

    @Override
    public boolean updateProfile(String userId, RegistrationModel model) throws IOException{
        User user  = userRepository.findById(userId).orElse(null);

        if(model.getName()!=null)
            user.setName(model.getName());

        if(model.getPassword()!=null)
            user.setPassword(model.getPassword());

        if(model.getEmail()!=null)
            user.setEmail(model.getEmail());

        if(model.getPhoneNumber()!=null)
            user.setPhoneNumber(model.getPhoneNumber());

        if(model.getFile()!=null)
            user.setProfilePhoto(model.getFile().getBytes());

        if(model.getAbout()!=null)
            user.setAbout(model.getAbout());

        try{
            userRepository.save(user);
        }catch(Exception exception){
            return false;
        }

        return true;

    }

    //get tweets by tweet ids
    public List<TweetProfile> getTweetsByTweetIds(List<BigInteger> tweetIds){
        if(tweetIds.size()>0){
            List<TweetProfile> myTweets = new ArrayList<>();
            for(BigInteger tweet_id:tweetIds){
                myTweets.add(this.getTweetByTweetId(tweet_id));
            }

            return myTweets;
        }
        return null;
    }


    //post a tweet
    @Override
    public Tweet postATweet(TweetModel tweetModel, String userId) throws IOException {
        Tweet tweet = this.addTweetContents(tweetModel, userId);

        Tweet returnedTweet = tweetRepository.save(tweet);

        //if tweet has tags
        if (tweetModel.getTags() != null) {
            for (String tag : tweetModel.getTags().split(",")) {
                query = "insert into tags values('" + tag.toUpperCase() + "'," + returnedTweet.getTweetId() + ")";

                jdbcTemplate.update(query);
            }
        }

        return returnedTweet;

    }

    @Override
    public Tweet makAComment(TweetModel commentModel, String userId) throws IOException {

        //add comment as a tweet
        Tweet returnedComment = this.postATweet(commentModel, userId);

        //insert it into comments table
        query = "insert into comments values(" + commentModel.getTweetId() + "," + returnedComment.getTweetId() + ")";
        jdbcTemplate.update(query);


        return returnedComment;

    }


    @Override
    public boolean updateTweet(String userId, TweetModel tweetModel) throws IOException {
        Tweet tweet = tweetRepository.findById(tweetModel.getTweetId()).orElse(null);

        if(tweet!=null){

            if(!(tweet.getUserId().equalsIgnoreCase(userId)))
                return false;

            if(tweetModel.getContent()!=null)
                tweet.setContent(tweetModel.getContent());

            if(tweetModel.getFile()!=null){
                tweet.setFileType(tweetModel.getFile().getContentType());
                tweet.setFileName(tweetModel.getFile().getOriginalFilename());
                tweet.setMedia(tweetModel.getFile().getBytes());

            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.now();

            tweet.setTweetTime(Timestamp.valueOf(dtf.format(localDateTime)));

            query = "delete from tags where tweet_id="+tweet.getTweetId();

            jdbcTemplate.update(query);

            tweetRepository.save(tweet);

            if(tweetModel.getTags()!=null){
                for (String tag : tweetModel.getTags().split(",")) {
                    query = "insert into tags values('" + tag.toUpperCase() + "'," + tweet.getTweetId() + ")";

                    jdbcTemplate.update(query);
                }
            }
        }

        return true;
    }

    public Tweet addTweetContents(TweetModel tweetModel, String userId) throws IOException {
        Tweet tweet = new Tweet();


        //if has file add it to tweet object
        if (tweetModel.getFile() != null) {
            tweet.setMedia(tweetModel.getFile().getBytes());
            tweet.setFileName(tweetModel.getFile().getOriginalFilename());
            tweet.setFileType(tweetModel.getFile().getContentType());
        }

        //set the userid
        tweet.setUserId(userId);

        //if tweet has some message content
        if (tweetModel.getContent() != null)
            tweet.setContent(tweetModel.getContent());

        tweet.setLikesCount(BigDecimal.valueOf(0));

        //set the time of tweet sending
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();

        tweet.setTweetTime(Timestamp.valueOf(dtf.format(localDateTime)));


        return tweet;
    }


    //profile url builder
    @Override
    public String profileURLBuilder(String userId){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/Twitter/Profile/").path(userId).toUriString();
    }

    //post url builder
    @Override
    public String postURLBuilder(BigInteger tweetId){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/Twitter/Post/").path(String.valueOf(tweetId)).toUriString();
    }

    @Override
    public boolean UnFollowAUser(String userId, String accId) {
            query = "delete from followers where user_id='"+accId+"' and follower_id='"+userId+"'";
            if(jdbcTemplate.update(query)<1)
                return false;

            this.updateFollowersCount(accId,-1);
            this.updateFollowingCount(userId,-1);

            return true;
    }

    //get a tweet based on the tweet id
    public TweetProfile getTweetByTweetId(BigInteger tweetId){
        query = "select name,u.user_id,verified,profile_photo,tweet_id,content,media,tweet_time,likes_count from users u inner join tweets t on t.user_id=u.user_id where tweet_id="+tweetId;

        return jdbcTemplate.query(query,(resultSet)->{
            if(!resultSet.next())
                return null;

            TweetProfile tweetProfile = new TweetProfile();
            tweetProfile.setUserName(resultSet.getString(1));
            tweetProfile.setUserId(resultSet.getString(2));
            tweetProfile.setVerified(resultSet.getBoolean(3));
            tweetProfile.setProfileURL(this.profileURLBuilder(resultSet.getString(2)));
            tweetProfile.setTweetId(BigInteger.valueOf(resultSet.getLong(5)));
            tweetProfile.setContent(resultSet.getString(6));
            tweetProfile.setPostURL(this.postURLBuilder(tweetId));
            tweetProfile.setTweetTime(resultSet.getTimestamp(8));
            tweetProfile.setLikeCount(resultSet.getBigDecimal(9));

            return tweetProfile;
        });
    }

}

