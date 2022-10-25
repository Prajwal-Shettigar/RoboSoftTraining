package com.prajwal.twitter.model;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

public class TweetModel {

    private BigInteger tweetId;
    private String content;
    private String tags;
    private MultipartFile file;


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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
