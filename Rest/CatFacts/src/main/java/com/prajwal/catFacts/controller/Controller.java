package com.prajwal.catFacts.controller;


import com.prajwal.catFacts.model.*;
import com.prajwal.catFacts.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {


    @Autowired
    Service service;



    //get cat facts
    @GetMapping("/Cats")
    public CatFact getCatFacts(){
        return service.getCatFact();
    }

    //get my current ip address
    @GetMapping("/MyIp")
    public Ip getMyIp(){
        return service.getMyIp();
    }

    //get info based on ip
    @GetMapping("/MyInfo/{ip}")
    public IpInfo getMyInfo(@PathVariable String ip){
        return service.getIpInfo(ip);
    }


    //tells a joke
    @GetMapping("/TellMeAJoke")
    public String tellAJoke(){
        Joke joke = service.getAJoke();
        return joke.getSetup()+"\n\n\n\n\n"+joke.getPunchline();
    }

    //get a photo of random dog
    @GetMapping(value="/Dog")
    public String getADogPhoto(){
        return "<img src='"+service.getDogPhoto().getMessage()+"' width='200px' height='200px'/>";
    }

    //get country based on name
    @GetMapping("/GuessCountry/{name}")
    public CountryDetails getCountryByName(@PathVariable String name){
        return service.getCountryByName(name);
    }


    //guess the gender based on name
    @GetMapping("/Gender/{name}")
    public Gender getGenderByName(@PathVariable String name){
        return service.guessGenderByName(name);
    }
}
