package com.prajwal.moviesystem.Controller;

import com.prajwal.moviesystem.Model.*;
import com.prajwal.moviesystem.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    private int sessionId;


    //admin login
    @GetMapping("/Login")
    public String login(@RequestBody Admin admin){
        if(adminService.adminLogin(admin)){
            sessionId = new Random().nextInt(3000,10000);
            return "successfully logged in.. your session id is '"+sessionId+"'";
        }

        return "unable to login wrong credentials...";
    }


    //add movie
    @PutMapping("/{sId}/Movie")
    public String addMovie(@PathVariable int sId,@RequestBody Movie movie){
        if(aunthentication(sId)){
            return updateSuccessfulOrNot(adminService.addMovie(movie));
        }

        return "unable to perform operation...";
    }

    //add theatre
    @PutMapping("/{sId}/Theatre")
    public String addTheatre(@PathVariable int sId,@RequestBody Theatre theatre){
        if(aunthentication(sId)){
           return updateSuccessfulOrNot(adminService.addTheatre(theatre));
        }

        return "unable to perform operation...";
    }

    //add show
    @PutMapping("/{sId}/Show")
    public String addShow(@PathVariable int sId, @RequestBody Show show){
        if(aunthentication(sId)){
            return updateSuccessfulOrNot(adminService.addShow(show));
        }

        return "unable to perform operation...";
    }


    //update a movie
    @PatchMapping("/{sId}/Movie")
    public String updateMovie(@PathVariable int sId,@RequestBody Movie movie){
        if(aunthentication(sId)){
            return updateSuccessfulOrNot(adminService.updateMovie(movie));
        }
        return "unable to perform operation...";
    }

    //get all movies
    @GetMapping("/{sId}/Movies")
    public ResponseEntity<List<Movie>> getAllMovies(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(adminService.getAllMovies()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get all theatres
    @GetMapping("/{sId}/Theatres")
    public ResponseEntity<List<Theatre>> getAllTheatres(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(adminService.getAllTheatres()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get all users
    @GetMapping("/{sId}/Users")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(adminService.getAllUsers()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get all history
    @GetMapping("/{sId}/History")
    public ResponseEntity<List<History>> getAllHistory(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(adminService.getAllHistory()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get all shows
    @GetMapping("/{sId}/Shows")
    public ResponseEntity<List<Show>> getAllShows(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(adminService.getAllShows()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get theatre by movie name
    @GetMapping("/{sId}/Theatre/{movieName}")
    public ResponseEntity<List<Theatre>> getTheatresByMovieName(@PathVariable int sId,@PathVariable String movieName){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(adminService.getTheatresByMovieName(movieName)));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }










    //non mapped methods for internal operations
    //for authentication based on session id
    public boolean aunthentication(int sId){
        if(sId==sessionId)
            return true;
        return false;
    }


    public String updateSuccessfulOrNot(int count){
        if(count>0)
            return "Successful...";
        return "Unsuccessful....";
    }
}
