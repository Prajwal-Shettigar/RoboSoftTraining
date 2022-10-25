package com.prajwal.twitter.model;

import java.math.BigDecimal;

public class UserProfile {
    private String userName;
    private String userId;
    private boolean verified;

    private String email;

    private BigDecimal phoneNumber;
    private String profileURL;
    private BigDecimal followingCount;
    private BigDecimal followersCount;
    private String about;

    private boolean following;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigDecimal phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
