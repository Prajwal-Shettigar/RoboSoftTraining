package com.prajwal.movie.Model;

import java.io.Serializable;
import java.sql.Timestamp;

/*
{
"id":1,
"theatreId":1,
"movieId":1,
"timings":"2022-05-31 12:30:23",
"screen_no":2
}
 */
public class Show implements Serializable {

    private int id;
    private int theatreId;
    private int movieId;
    private Timestamp timings;
    private int seats;
    private int screen_no;


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

    public void setTimings(String timings) {
        this.timings = Timestamp.valueOf(timings);
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
