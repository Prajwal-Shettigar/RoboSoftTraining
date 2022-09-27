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
        String selectStatement = "select id,name,description,duration,rating from movie where name='"+movieName+"'";

        return jdbcTemplate.query(selectStatement,(resultSet)->{
            if(!resultSet.next())
                return null;

            Movie movie = new Movie();

            movie.setId(resultSet.getInt(1));
            movie.setName(resultSet.getString(2));
            movie.setDescription(resultSet.getString(3));
            movie.setDuration(resultSet.getInt(4));
            movie.setRating(resultSet.getDouble(5));


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

        String getCostOfTicket = "select cost from theatre inner join shows on theatre.id=shows.theatre_id where shows.id="+showId;

        String decrementSeats = "update shows set seats="+seatsNow+" where id="+showId;

        String addToHistory = "insert into history(user_id,show_id,seats_booked,total_cost,paid) values(?,?,?,?,?)";

        double costPerTicket = jdbcTemplate.queryForObject(getCostOfTicket, Double.class);

        jdbcTemplate.update(decrementSeats);

        double totalCost = costPerTicket*noOfSeats;

        jdbcTemplate.update(addToHistory,(preparedStatement)->{
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,showId);
            preparedStatement.setInt(3,noOfSeats);
            preparedStatement.setDouble(4,totalCost);
            preparedStatement.setBoolean(5,false);
        });

        return "successfully booked "+noOfSeats+" seats... and the total cost of those tickets = "+totalCost;
    }


    //get movie recomendations based on id
    public List<MovieRecomendation> getMovieRecomendations(int userId){
        String selectUser = "select location from user where id="+userId;

        String location = jdbcTemplate.queryForObject(selectUser, String.class);

        return getMovieRecomendationBasedonLocation(location);
    }


    //rate a movie
    public String rateAMovie(int userId,int movieId,int rating){

        if(rating<0 || rating>5){
            return "ratings must be within 0 and 5...";
        }
        String checkRatings = "select * from ratings where user_id="+userId+" and movie_id="+movieId;

        Ratings  ratings = jdbcTemplate.query(checkRatings,(resultSet)->{
            Ratings r = new Ratings();
            if(!resultSet.next()){
                return null;
            }
            r.setMovieId(resultSet.getInt(1));
            r.setUserId(resultSet.getInt(2));
            r.setRating(resultSet.getInt(3));

            return r;
        });

        if(ratings!=null){
            return "can add review to a movie only once..";
        }

        String addRatings = "insert into ratings values(?,?,?)";
        jdbcTemplate.update(addRatings,movieId,userId,rating);

        String getAvgRating = "select avg(rating) from ratings where movie_id="+movieId;

        double avgRating = jdbcTemplate.queryForObject(getAvgRating, Double.class);

        String addTotalRating = "update movie set rating="+avgRating+" where id="+movieId;

        jdbcTemplate.update(addTotalRating);

        return "Review added successfully...";

    }


    //get movie recomendation based location
    public List<MovieRecomendation> getMovieRecomendationBasedonLocation(String location){
        String selectQuery = "select name,location,cost,shows.id,theatre_id,movie_id,timings,seats,screen_no from theatre inner join shows on theatre.id=shows.theatre_id where location='"+location+"'";

        return jdbcTemplate.query(selectQuery,new BeanPropertyRowMapper<>(MovieRecomendation.class));
    }


    //add balance
    public String addBalance(int userId,double amount){
        double currentBalance = getBalance(userId);

        String updateBalance = "update user set wallet="+(currentBalance+amount)+" where id="+userId;

        jdbcTemplate.update(updateBalance);

        return amount+" added successfully your current balance is ="+(currentBalance+amount);
    }

    //get balance based on user id
    public double getBalance(int userId){
        String query = "select wallet from user where id="+userId;
        return jdbcTemplate.queryForObject(query, Double.class);
    }


    //make payment
    public String makePayment(int history_id,int userId){
        double currentBalance = getBalance(userId);

        if(jdbcTemplate.queryForObject("select paid from history where history_id="+history_id, Boolean.class)){
            return "you have already paid for this booking...";
        }

        double amountToBePaid = jdbcTemplate.queryForObject("select total_cost from history where history_id="+history_id,Double.class);

        if(amountToBePaid>currentBalance){
            return "insufficient balance amount add money to wallet...";
        }

        String changeWallet = "update user set wallet="+(currentBalance-amountToBePaid)+" where id="+userId;

        String updateHistory = "update history set paid=true where history_id="+history_id;

        jdbcTemplate.update(updateHistory);
        jdbcTemplate.update(changeWallet);

        return "payment of "+amountToBePaid+" has been made successfully...your current balance is "+(currentBalance-amountToBePaid);


    }


    //get available seats based on show id
    public int getAvailableSeats(int showId){
        String selectQuery = "select seats from shows where id="+showId;

        return jdbcTemplate.queryForObject(selectQuery, Integer.class);
    }


    //get user history based on id
    public List<CompleteHistory> getHistoryById(int userId){
        String selectQuery = "select user_id,show_id,seats_booked,theatre_id,movie_id,timings,seats,screen_no,theatre.name,location,cost,movie.name,description,duration,total_cost,history_id,paid from history inner join shows on history.show_id=shows.id inner join theatre on shows.theatre_id=theatre.id inner join movie on shows.movie_id=movie.id where user_id="+userId;


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
            completeHistory.setTotal_cost(resultSet.getDouble(15));
            completeHistory.setHistory_id(resultSet.getInt(16));
            completeHistory.setPaid(resultSet.getBoolean(17));
            return completeHistory;
        });

    }
}
