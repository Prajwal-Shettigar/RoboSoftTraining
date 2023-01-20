package com.prajwal.userService.service;


import com.prajwal.userService.entity.User;
import com.prajwal.userService.model.Department;
import com.prajwal.userService.model.UserDepoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prajwal.userService.repository.UserRepository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{


    @Autowired
    UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    //get all the users
    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    //get user by id
    @Override
    public User getUserById(long id){
        return userRepository.findById(id).orElse(null);
    }


    //save a user
    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    //get the user along with department
    @Override
    public UserDepoResponse getUserAlongWithDepartment(long id){
        User user = this.getUserById(id);

        if(user==null)
            return null;

        Department department =  restTemplate.getForObject("http://DEPARTMENT-SERVICE/Department/"+user.getDepartmentId(), Department.class);

        System.out.println(department);
        System.out.println(user);

        UserDepoResponse userDepoResponse = new UserDepoResponse(user,department);
        System.out.println(userDepoResponse);

        return userDepoResponse;

    }
}
