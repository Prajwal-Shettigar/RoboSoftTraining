package com.prajwal.twitter.model;

import java.math.BigDecimal;

public class UserProfile {
    private String userName;
    private String userId;
    private boolean verified;
    private String profileURL;
    private BigDecimal followingCount;
    private BigDecimal followersCount;
    private String about;

    public UserProfile() {
    }

    public UserProfile(String userName, String userId, boolean verified, String profileURL, BigDecimal followingCount, BigDecimal followersCount, String about) {
        this.userName = userName;
        this.userId = userId;
        this.verified = verified;
        this.profileURL = profileURL;
        this.followingCount = followingCount;
        this.followersCount = followersCount;
        this.about = about;
    }

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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public BigDecimal getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(BigDecimal followingCount) {
        this.followingCount = followingCount;
    }

    public BigDecimal getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(BigDecimal followersCount) {
        this.followersCount = followersCount;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
