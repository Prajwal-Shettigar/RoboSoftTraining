package com.prajwal.userService.service;

import com.prajwal.userService.entity.User;
import com.prajwal.userService.model.UserDepoResponse;

import java.util.List;

public interface UserService {
    //get all the users
    List<User> getAllUsers();

    //get user by id
    User getUserById(long id);

    //save a user
    User saveUser(User user);

    //get the user along with department
    UserDepoResponse getUserAlongWithDepartment(long id);
}
