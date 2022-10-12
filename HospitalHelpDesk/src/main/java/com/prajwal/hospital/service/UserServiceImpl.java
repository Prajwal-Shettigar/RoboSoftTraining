package com.prajwal.hospital.service;

import com.prajwal.hospital.model.Insurance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Insurance insureUser(Insurance insurance) {
        String query = "insert into insurance(name,age,gender,claim_amount) values(?,?,?,?)";


        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,insurance.getName());
            preparedStatement.setInt(2,insurance.getAge());
            preparedStatement.setString(3,insurance.getGender());
            preparedStatement.setDouble(4,insurance.getClaimAmount());
        });

        return getLastInsured();
    }



    //get last insurance
    public Insurance getLastInsured(){
        String query = "select * from insurance where id=(select max(id) from insurance)";

        return jdbcTemplate.query(query,(resultSet)->{
            Insurance insurance =  new Insurance();
            if(!resultSet.next())
                return null;

            insurance.setId(resultSet.getInt(1));
            insurance.setName(resultSet.getString(2));
            insurance.setAge(resultSet.getInt(3));
            insurance.setGender(resultSet.getString(4));
            insurance.setClaimAmount(resultSet.getDouble(5));

            return insurance;
        });
    }
}
