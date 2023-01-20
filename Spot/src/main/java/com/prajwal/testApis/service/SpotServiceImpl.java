package com.prajwal.testApis.service;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class SpotServiceImpl implements SpotService{


    @Override
    public void callSpot(String keyword) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://spott.p.rapidapi.com/places/autocomplete?limit=10&skip=0&country=IN&q="+keyword+"&type=CITY"))
                .header("X-RapidAPI-Key", "ef178eb6damshdf7a9c4a68f134fp11b011jsnf1c449814664")
                .header("X-RapidAPI-Host", "spott.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
