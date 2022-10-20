package com.prajwal.twitter.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity(name="Tweets")
@Table(name="Tweets")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger tweetId;

//    @ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL)
//    @JoinColumn(name="user_id",referencedColumnName = "userId")
    private String userId;

    private String content;
    private byte[] media;
    private Timestamp tweetTime;
    private BigDecimal likesCount;
    private String fileType;

    private String fileName;

    public BigInteger getTweetId() {
        return tweetId;
    }

    public void setTweetId(BigInteger tweetId) {
        this.tweetId = tweetId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getMedia() {
        return media;
    }

    public void setMedia(byte[] media) {
        this.media = media;
    }

    public Timestamp getTweetTime() {
        return tweetTime;
    }

    public void setTweetTime(Timestamp tweetTime) {
        this.tweetTime = tweetTime;
    }

    public BigDecimal getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(BigDecimal likesCount) {
        this.likesCount = likesCount;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
