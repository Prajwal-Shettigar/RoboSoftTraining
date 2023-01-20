package com.prajwal.testApis.controller;

import com.prajwal.testApis.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SpotController {

    @Autowired
    SpotService spotService;

    @GetMapping("/Spot/{keyWord}")
    public HttpStatus callSpot(@PathVariable("keyWord") String keyWord) throws IOException, InterruptedException {
        spotService.callSpot(keyWord);

        return HttpStatus.OK;






    }
}
