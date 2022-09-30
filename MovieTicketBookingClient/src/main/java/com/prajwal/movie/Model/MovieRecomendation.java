package com.prajwal.movie.Model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MovieRecomendation implements Serializable {

    private String name;
    private String location;

    private double cost;

    private int id;
    private int theatreId;
    private int movieId;
    private Timestamp timings;
    private int seats;
    private int screen_no;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Timestamp getTimings() {
        return timings;
    }

    public void setTimings(Timestamp timings) {
        this.timings = timings;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getScreen_no() {
        return screen_no;
    }

    public void setScreen_no(int screen_no) {
        this.screen_no = screen_no;
    }
}
