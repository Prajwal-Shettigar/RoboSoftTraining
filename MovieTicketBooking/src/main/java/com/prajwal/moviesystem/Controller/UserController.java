package com.prajwal.moviesystem.Controller;

import com.prajwal.moviesystem.Model.*;
import com.prajwal.moviesystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    private int sessionId;

    private int userId;




    //login a user
    @GetMapping("/Login")
    public String userLogin(@RequestBody User user){
        if(userService.userLogin(user)){
            sessionId = new Random().nextInt(10000,20000);
            userId = user.getId();

            return "login successful... your session id is '"+sessionId+"'";
        }

        return "incorrect login credentials....";
    }



    //register a user
    @GetMapping("/Register")
    public String userRegister(@RequestBody User user){
        int id = userService.userRegistration(user);

        if(id>0){
            userId = id;
            sessionId = new Random().nextInt(10000,20000);
            return "registration successful... your login id is '"+id+"' and your session id is "+sessionId;
        }

        return "registration unsuccessful check the credentials given properly...";
    }



    //get all shows
    @GetMapping("/{sId}/Shows")
    public ResponseEntity<List<Show>> getAllShows(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getAllShows()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get theatre by movie name
    @GetMapping("/{sId}/Theatre/{movieName}")
    public ResponseEntity<List<Theatre>> getTheatresByMovieName(@PathVariable int sId, @PathVariable String movieName){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getTheatresByMovieName(movieName)));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get movie details by name
    @GetMapping("/{sId}/Movie/{movieName}")
    public ResponseEntity<Movie> getMovieDetails(@PathVariable int sId, @PathVariable String movieName){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getMovieByName(movieName)));
        }

        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //book an ticket
    @PostMapping("/{sId}/Booking/{showId}/{noOfSeats}")
    public ResponseEntity<String> bookTickets(@PathVariable int sId,@PathVariable int showId,@PathVariable int noOfSeats){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.bookAnTicket(userId,showId,noOfSeats)));
        }

        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get movie recomendation
    @GetMapping("/{sId}/Movies/Recommend")
    public ResponseEntity<List<MovieRecomendation>> getRecomendation(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getMovieRecomendations(userId)));
        }

        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get movie recommendation by location
    @GetMapping("/{sId}/Movies/Recommend/{location}")
    public ResponseEntity<List<MovieRecomendation>> getRecomendationByLocation(@PathVariable int sId,@PathVariable String location){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getMovieRecomendationBasedonLocation(location)));
        }

        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get all movies
    @GetMapping("/{sId}/Movies")
    public ResponseEntity<List<Movie>> getAllMovies(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getAllMovies()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get all theatres
    @GetMapping("/{sId}/Theatres")
    public ResponseEntity<List<Theatre>> getAllTheatres(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getAllTheatres()));
        }
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
    }

    //get history of current user
    @GetMapping("/{sId}/History")
    public ResponseEntity<List<CompleteHistory>> getCurrentHistory(@PathVariable int sId){
        if(aunthentication(sId)){
            return ResponseEntity.of(Optional.of(userService.getHistoryById(userId)));
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
