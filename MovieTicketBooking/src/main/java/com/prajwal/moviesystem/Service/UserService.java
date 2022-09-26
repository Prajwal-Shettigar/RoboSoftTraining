package com.prajwal.moviesystem.Service;

import com.prajwal.moviesystem.Model.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends MainService{

    //user login using id and password
    public boolean userLogin(User user){

        User dbUser = getUserByPhoneNumber(user);

        if(dbUser==null){
            return false;
        }

        if(user.getId()==dbUser.getId())
            return true;

        return false;
    }


    //user registration
    public int userRegistration(User user){
        if(getUserByPhoneNumber(user)!=null){
            return 0;
        }

        String insertQuery = "insert into user(fname,lname,phonenumber,location,email) values(?,?,?,?,?)";

        jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setString(1,user.getFname());
            preparedStatement.setString(2,user.getLname());
            preparedStatement.setLong(3,user.getPhoneNumber());
            preparedStatement.setString(4,user.getLocation());
            preparedStatement.setString(5,user.getEmail());
        });

        int id = jdbcTemplate.queryForObject("select id from user where phonenumber="+user.getPhoneNumber(),Integer.class);

        return id;
    }


    //get user by phone number
    public User getUserByPhoneNumber(User user){
        String selectQuery = "select id from user where phonenumber="+user.getPhoneNumber();

        User dbUser = jdbcTemplate.query(selectQuery,(resultSet)->{
            if(!resultSet.next()){
                return null;
            }
            User u = new User();

            u.setId(resultSet.getInt(1));
            return u;
        });

        return dbUser;
    }

    //get individual movie details by movie name
    public Movie getMovieByName(String movieName){
        String selectStatement = "select id,name,description,duration from movie where name='"+movieName+"'";

        return jdbcTemplate.query(selectStatement,(resultSet)->{
            if(!resultSet.next())
                return null;

            Movie movie = new Movie();

            movie.setId(resultSet.getInt(1));
            movie.setName(resultSet.getString(2));
            movie.setDescription(resultSet.getString(3));
            movie.setDuration(resultSet.getInt(4));


            return movie;


        });
    }

    //book a ticket based on show id
    public String bookAnTicket(int userId,int showId,int noOfSeats){

        int availableSeats = getAvailableSeats(showId);

        int seatsNow = availableSeats-noOfSeats;

        if(seatsNow<0){
            return "Only "+availableSeats+" seats are available Required number of Seats not available...";
        }

        String decrementSeats = "update shows set seats="+seatsNow+" where id="+showId;

        String addToHistory = "insert into history(user_id,show_id,seats_booked) values(?,?,?)";

        jdbcTemplate.update(decrementSeats);

        jdbcTemplate.update(addToHistory,(preparedStatement)->{
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,showId);
            preparedStatement.setInt(3,noOfSeats);
        });

        return "successfully booked "+noOfSeats+" seats...";
    }


    //get movie recomendations based on id
    public List<MovieRecomendation> getMovieRecomendations(int userId){
        String selectUser = "select location from user where id="+userId;

        String location = jdbcTemplate.queryForObject(selectUser, String.class);

        return getMovieRecomendationBasedonLocation(location);
    }


    //get movie recomendation based location
    public List<MovieRecomendation> getMovieRecomendationBasedonLocation(String location){
        String selectQuery = "select name,location,cost,shows.id,theatre_id,movie_id,timings,seats,screen_no from theatre inner join shows on theatre.id=shows.theatre_id where location='"+location+"'";

        return jdbcTemplate.query(selectQuery,new BeanPropertyRowMapper<>(MovieRecomendation.class));
    }



    //get available seats based on show id
    public int getAvailableSeats(int showId){
        String selectQuery = "select seats from shows where id="+showId;

        return jdbcTemplate.queryForObject(selectQuery, Integer.class);
    }


    //get user history based on id
    public List<CompleteHistory> getHistoryById(int userId){
        String selectQuery = "select user_id,show_id,seats_booked,theatre_id,movie_id,timings,seats,screen_no,theatre.name,location,cost,movie.name,description,duration from history inner join shows on history.show_id=shows.id inner join theatre on shows.theatre_id=theatre.id inner join movie on shows.movie_id=movie.id where user_id="+userId;


        return jdbcTemplate.query(selectQuery,(resultSet,noOfRows)->{
            CompleteHistory completeHistory = new CompleteHistory();
            completeHistory.setUserId(resultSet.getInt(1));
            completeHistory.setShowId(resultSet.getInt(2));
            completeHistory.setSeatsBooked(resultSet.getInt(3));
            completeHistory.setTheatreId(resultSet.getInt(4));
            completeHistory.setMovieId(resultSet.getInt(5));
            completeHistory.setTimings(resultSet.getTimestamp(6));
            completeHistory.setSeats(resultSet.getInt(7));
            completeHistory.setScreen_no(resultSet.getInt(8));
            completeHistory.setTheatreName(resultSet.getString(9));
            completeHistory.setLocation(resultSet.getString(10));
            completeHistory.setCost(resultSet.getDouble(11));
            completeHistory.setMovieName(resultSet.getString(12));
            completeHistory.setDescription(resultSet.getString(13));
            completeHistory.setDuration(resultSet.getInt(14));
            return completeHistory;
        });

    }
}
