package com.prajwal.twitter.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class TweetProfile {


    //user arguments
    private String userName;
    private String userId;
    private String profileURL;
    private boolean verified;

    //tweet arguments
    private BigInteger tweetId;
    private String content;
    private String postURL;
    private Timestamp tweetTime;


    private BigDecimal likeCount;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public BigInteger getTweetId() {
        return tweetId;
    }

    public void setTweetId(BigInteger tweetId) {
        this.tweetId = tweetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostURL() {
        return postURL;
    }

    public void setPostURL(String postURL) {
        this.postURL = postURL;
    }

    public Timestamp getTweetTime() {
        return tweetTime;
    }

    public void setTweetTime(Timestamp tweetTime) {
        this.tweetTime = tweetTime;
    }

    public BigDecimal getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(BigDecimal likeCount) {
        this.likeCount = likeCount;
    }
}
