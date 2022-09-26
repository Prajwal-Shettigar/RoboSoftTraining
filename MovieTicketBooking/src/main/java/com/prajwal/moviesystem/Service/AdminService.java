package com.prajwal.moviesystem.Service;

import com.prajwal.moviesystem.Model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends MainService{

    //admin login
    public Boolean adminLogin(Admin admin){
        String selectQuery = "select * from admin where id="+admin.getId();

        Admin dbAdmin = jdbcTemplate.query(selectQuery,(resultSet)->{

            if(!resultSet.next())
                return null;

            Admin ad = new Admin();
            ad.setPassword(resultSet.getString(2));

            return ad;
        });

        if(dbAdmin==null)
            return false;

        if(admin.getPassword().equalsIgnoreCase(dbAdmin.getPassword())){
            return true;
        }

        return false;
    }



    //add a movie
    public int addMovie(Movie movie){
        String insertQuery = "insert into movie(name,description,duration) values(?,?,?)";

        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setString(1,movie.getName());
            preparedStatement.setString(2,movie.getDescription());
            preparedStatement.setInt(3,movie.getDuration());
        });
    }

    //add theatre
    public int addTheatre(Theatre theatre){
        String insertQuery = "insert into theatre(name,location,cost) values(?,?,?)";
        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setString(1,theatre.getName());
            preparedStatement.setString(2,theatre.getLocation());
            preparedStatement.setDouble(3,theatre.getCost());
        });
    }

    //add shows
    public int addShow(Show show){
        String insertQuery = "insert into shows(theatre_id,movie_id,timings,screen_no) values(?,?,?,?)";
        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setInt(1,show.getTheatreId());
            preparedStatement.setInt(2,show.getMovieId());
            preparedStatement.setTimestamp(3,show.getTimings());
            preparedStatement.setInt(4,show.getScreen_no());
        });
    }


    //update movies
    public int updateMovie(Movie movie){
        String selectQuery = "select * from movie where id="+movie.getId();

        Movie dbMovie = jdbcTemplate.queryForObject(selectQuery,new BeanPropertyRowMapper<>(Movie.class));

        if(dbMovie==null){
            return 0;
        }

        if(movie.getName()!=null){
            dbMovie.setName(movie.getName());
        }

        if(movie.getDescription()!=null){
            dbMovie.setDescription(movie.getDescription());
        }

        if(movie.getDuration()!=0){
            dbMovie.setDuration(movie.getDuration());
        }

        String updateQuery = "update movie set name=?,description=? ,duration=? where id="+movie.getId();

        return jdbcTemplate.update(updateQuery,(preparedStatement)->{
            preparedStatement.setString(1,dbMovie.getName());
            preparedStatement.setString(2,dbMovie.getDescription());
            preparedStatement.setInt(3,dbMovie.getDuration());
        });
    }

    //get all users
    public List<User> getAllUsers(){
        return jdbcTemplate.query("select * from user",new BeanPropertyRowMapper<>(User.class));
    }

    //get all history
    public List<History> getAllHistory(){
        return jdbcTemplate.query("select * from history",new BeanPropertyRowMapper<>(History.class));
    }

}
