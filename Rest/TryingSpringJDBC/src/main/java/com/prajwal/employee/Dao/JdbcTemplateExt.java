package com.prajwal.employee.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateExt extends JdbcTemplate {

    @Autowired
    public JdbcTemplateExt(DriverManagerDataSource driverManagerDataSource){
        super(driverManagerDataSource);
    }
}
