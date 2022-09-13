package com.prajwal.employee.Dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;


@Component
public class DataSourceExt extends DriverManagerDataSource {



    public DataSourceExt(){

        super("jdbc:mysql://localhost:3306/forspring","root","root");

    }
}
