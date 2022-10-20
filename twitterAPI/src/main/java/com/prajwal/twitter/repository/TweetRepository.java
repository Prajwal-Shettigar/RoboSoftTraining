package com.prajwal.twitter.repository;

import com.prajwal.twitter.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, BigInteger> {

    List<Tweet> findByUserId(String userId);
}
