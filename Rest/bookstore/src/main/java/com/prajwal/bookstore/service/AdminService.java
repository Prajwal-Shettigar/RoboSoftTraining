package com.prajwal.bookstore.service;

import com.prajwal.bookstore.model.Admin;
import com.prajwal.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService extends MainService{


    //authenticate an admin
    public boolean authenticateAdmin(Admin admin){
        Admin admininDB =  jdbcTemplate.query("select * from admins where adminId="+admin.getAdminId(),(resultSet)->{
            Admin retAdmin = new Admin();
            resultSet.next();
            retAdmin.setAdminId(resultSet.getInt(1));
            retAdmin.setName(resultSet.getString(2));
            retAdmin.setPassword(resultSet.getString(3));

            return retAdmin;

        });

        if((admin.getName().equalsIgnoreCase(admininDB.getName())) && (admin.getPassword().equalsIgnoreCase(admininDB.getPassword()))){
            return true;
        }

        return false;
    }

    //insert a book into the books table
    public int insertABook(Book book){
        String insertQuery = "insert into books(name,price,available) values(?,?,?)";

        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setString(1,book.getName());
            preparedStatement.setInt(2,book.getPrice());
            preparedStatement.setBoolean(3,book.isAvailable());
        });
    }

    //remove a book based on id from books table
    public int removeABookById(int bookId){
        String deleteQuery = "delete from books where bookId="+bookId;

        return jdbcTemplate.update(deleteQuery);
    }


    //update the price of a book using the id
    public int updatePriceById(int bookId,int price){
        String updateQuery = "update books set price="+price+" where bookId="+bookId;
        return jdbcTemplate.update(updateQuery);
    }



}
