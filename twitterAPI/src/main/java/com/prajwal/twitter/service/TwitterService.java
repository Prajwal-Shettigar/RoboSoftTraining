package com.prajwal.twitter.service;

import com.prajwal.twitter.entity.Users;

public interface TwitterService {

    Users registerUser(Users users);

    Users getUserById(String userId);

}
