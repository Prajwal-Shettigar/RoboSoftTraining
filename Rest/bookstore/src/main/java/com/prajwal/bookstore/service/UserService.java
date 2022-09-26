package com.prajwal.bookstore.service;

import com.prajwal.bookstore.model.Book;
import com.prajwal.bookstore.model.Cart;
import com.prajwal.bookstore.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends MainService{



    //registers an user
    public int insertAnUser(User user){
        String insertQuery = "insert into user(name,password) values(?,?)";
        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
        });
    }


    //authenticate an user
    public boolean authenticateAnUser(User user){
        String selectQuery = "select * from user where id="+user.getId();

        User dbUser = jdbcTemplate.query(selectQuery,(resultSet)->{
            User returnUser = new User();
            resultSet.next();

            returnUser.setId(resultSet.getInt(1));
            returnUser.setName(resultSet.getString(2));
            returnUser.setPassword(resultSet.getString(3));
            return returnUser;
        }) ;

        if((user.getName().equalsIgnoreCase(dbUser.getName()))&&(user.getPassword().equalsIgnoreCase(dbUser.getPassword()))){
            return true;
        }

        return false;
    }



    //add books into shopping cart
    public int addBooksIntoCart(Cart cart){

        String checkIfAvailable = "select availability from books where bookId="+cart.getBookId();

        //if book not available then ndo not insert into car
        if(!(jdbcTemplate.query(checkIfAvailable,(resultSet)->{
            resultSet.next();
            return resultSet.getBoolean(1);
        }))){
            return 0;
        }
        String insertQuery = "insert into cart values(?,?)";

        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setInt(1,cart.getUserId());
            preparedStatement.setInt(2,cart.getBookId());
        });
    }


    //remove books from shopping cart
    public int removeCartBooks(Cart cart){
        String deleteQuery = "delete from cart where userId="+cart.getUserId()+"and bookId="+cart.getBookId();

        return jdbcTemplate.update(deleteQuery);
    }

    //buy books
    public int buyBooks(int userId){
        String selectFromCart = "select * from cart where id="+userId;
        String removerFromCart = "delete from cart where id="+userId;
        String insertIntoPurchased = "insert into purchased values(?,?)";
        String updateBooksAvailability = "update books set available=0 where bookId=";


        //get all cart content for that user
        List<Cart> shoppingCart = jdbcTemplate.query(selectFromCart,new BeanPropertyRowMapper<>(Cart.class));


        //remove everything from cart
        jdbcTemplate.update(removerFromCart);

        int insertColumnCount=0;


        //add everything from cart onto the purchased table for that user
        for(Cart cart : shoppingCart){
            insertColumnCount+=jdbcTemplate.update(insertIntoPurchased,(preparedStatement)->{
                preparedStatement.setInt(1,cart.getUserId());
                preparedStatement.setInt(2,cart.getBookId());
                jdbcTemplate.update(updateBooksAvailability+cart.getBookId());
            });
        }

        //change the availability of books to not available
        return insertColumnCount;

    }

}
