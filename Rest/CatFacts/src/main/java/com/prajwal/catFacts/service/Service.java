package com.prajwal.catFacts.service;

import com.prajwal.catFacts.model.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;


@org.springframework.stereotype.Service
public class Service {

    RestTemplate restTemplate;

    public Service() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    //get facts related to cat
    public CatFact getCatFact(){


        return restTemplate.getForObject("https://catfact.ninja/fact", CatFact.class);
    }

    //get ip address
    public Ip getMyIp(){

        return restTemplate.getForObject("https://api.ipify.org?format=json", Ip.class);
    }

    //get info based on my ip
    public IpInfo getIpInfo(String ipAddress){
        return restTemplate.getForObject("https://ipinfo.io/"+ipAddress+"/geo", IpInfo.class);
    }

    //get a randome joke
    public Joke getAJoke(){
        return restTemplate.getForObject("https://official-joke-api.appspot.com/random_joke",Joke.class);
    }

    //get a random dog photo
    public Dog getDogPhoto(){
        return restTemplate.getForObject("https://dog.ceo/api/breeds/image/random", Dog.class);
    }

    //guess nationality based on name
    public CountryDetails getCountryByName(String name){
        return restTemplate.getForObject("https://api.nationalize.io?name="+name, CountryDetails.class);
    }

    //guess gender by name
    public Gender guessGenderByName(String name){
        return restTemplate.getForObject("https://api.genderize.io?name="+name, Gender.class);
    }
}
