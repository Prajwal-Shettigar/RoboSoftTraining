package com.prajwal.movie.controller;


import com.prajwal.movie.Model.Show;
import com.prajwal.movie.Model.User;
import com.prajwal.movie.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {


    @Autowired
    ClientService clientService;



    //user login
    @PostMapping("/Login")
    public String userLogin(@RequestBody User user){
        return clientService.userLogin(user);
    }

    //user register
    @PostMapping("/Register")
    public String userRegister(@RequestBody User user){
        return  clientService.userRegister(user);
    }

    //get shows
    @GetMapping("/{sId}/Shows")
    public List<Show> getShows(@PathVariable int sId){
        return clientService.getShows(sId);
    }

//    //get theatre by movie name
//    @GetMapping("/{sId}/Theatre/{movieName}")

}
