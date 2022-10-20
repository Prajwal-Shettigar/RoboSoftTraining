package com.prajwal.twitter.service;

import com.prajwal.twitter.entity.Tweet;
import com.prajwal.twitter.entity.User;
import com.prajwal.twitter.model.TweetModel;
import com.prajwal.twitter.model.TweetProfile;
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
        User returnedUser = this.getUserById(user.getUserId());


        //if user exists and password matches then return true otherwise return false
        if (returnedUser != null) {
            if (user.getPassword().equalsIgnoreCase(returnedUser.getPassword()))
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
    public List<TweetProfile> getTweetsByTag(String tag, int limit) {
        query = "select tweet_id from tags where tag='"+tag+"' order by tweet_id desc limit "+limit;
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

