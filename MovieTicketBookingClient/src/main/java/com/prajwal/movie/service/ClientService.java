package com.prajwal.movie.service;


import com.prajwal.movie.Model.Show;
import com.prajwal.movie.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService {

    RestTemplate restTemplate;

    @Autowired
    ClientService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }

    //user login
    public String userLogin(User user){
        HttpEntity<User> userHttpEntity = new HttpEntity<User>(user);

        return restTemplate.postForObject("http://localhost:8091/User/Login",userHttpEntity,String.class);
    }

    //user registration
    public String userRegister(User user){
        HttpEntity<User> userHttpEntity = new HttpEntity<User>(user);
        return restTemplate.postForObject("http://localhost:8091/User/Register",userHttpEntity, String.class);
    }

    //get shows
    public List<Show> getShows(int sId){
        return (ArrayList<Show>)restTemplate.getForObject("http://localhost:8091/User/"+sId+"/Shows", ArrayList.class);
    }

}
