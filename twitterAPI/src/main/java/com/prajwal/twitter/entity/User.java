package com.prajwal.twitter.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;

/*
{
"name":"prajwal",
"userId":"prajwal01",
"password":"prajwalsu",
"email":"prajwal@something.com",
"phoneNumber":7619376175
}
 */


@Entity(name="Users")
@Table(name = "Users")
public class User {
    private String name;
    @Id
    private String userId;
    private String password;

    private boolean verified;
    private String email;
    private BigDecimal phoneNumber;
    @Lob
    private byte[] profilePhoto;


    private BigDecimal followersCount;

    private BigDecimal followingCount;

    private String about;


    public User() {
    }

    public User(String name, String userId, String password, boolean verified, String email, BigDecimal phoneNumber, BigDecimal followersCount, BigDecimal followingCount) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        this.verified = verified;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public BigDecimal getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigDecimal phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public BigDecimal getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(BigDecimal followersCount) {
        this.followersCount = followersCount;
    }

    public BigDecimal getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(BigDecimal followingCount) {
        this.followingCount = followingCount;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
