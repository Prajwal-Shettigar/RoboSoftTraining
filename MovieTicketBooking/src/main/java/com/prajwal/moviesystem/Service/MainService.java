package com.prajwal.moviesystem.Service;

import com.prajwal.moviesystem.Model.Movie;
import com.prajwal.moviesystem.Model.Show;
import com.prajwal.moviesystem.Model.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //get theatres based on movie name
    public List<Theatre> getTheatresByMovieName(String movieName){

        //get theatre id where that movie is running
        String selectQueryOnShows = "select theatre_id from shows inner join movie on shows.movie_id=movie.id where movie.name='"+movieName+"'";

        List<Integer> theatreIds = jdbcTemplate.query(selectQueryOnShows,(resultSet,noOfRows)->{
            return resultSet.getInt(1);
        });

        if(theatreIds.size()<1){
            return new ArrayList<>();
        }

        //get details of theatres based on the theatre ids extracted earlier..
        List<Theatre> theatres = new ArrayList<>();
        String selectQueryForTheatre = "select * from theatre where id=";

        for(int tId:theatreIds){
            theatres.add(jdbcTemplate.queryForObject(selectQueryForTheatre+tId,new BeanPropertyRowMapper<>(Theatre.class)));
        }

        return theatres;
    }

    //get all movies
    public List<Movie> getAllMovies(){
        return jdbcTemplate.query("select * from movie",new BeanPropertyRowMapper<>(Movie.class));
    }

    //get all theatres
    public List<Theatre> getAllTheatres(){
        return jdbcTemplate.query("select * from theatre",new BeanPropertyRowMapper<>(Theatre.class));
    }

    //get all shows
    public List<Show> getAllShows(){
        return jdbcTemplate.query("select * from shows",new BeanPropertyRowMapper<>(Show.class));
    }




}
