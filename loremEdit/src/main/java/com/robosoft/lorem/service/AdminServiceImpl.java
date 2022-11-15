package com.robosoft.lorem.service;


import com.robosoft.lorem.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{


    @Autowired
    JdbcTemplate jdbcTemplate;


    String query;

    @Override
    public boolean changeRole(int userId, String role) {

        String userRole = getRole(role.toUpperCase());

        if(userRole.equalsIgnoreCase("none"))
            return false;

        query = "update user set role='"+userRole+"' where userId="+userId;

        if(jdbcTemplate.update(query)>0)
            return true;

        return false;

    }


    public String getRole(String role){
        switch (role){
            case "ADMIN"-> {return Role.ROLE_ADMIN.toString();}

            case "USER"->{return Role.ROLE_USER.toString();}

            case "MERCHANT"->{return Role.ROLE_MERCHANT.toString();}

            default -> {return "None";}
        }
    }
}
