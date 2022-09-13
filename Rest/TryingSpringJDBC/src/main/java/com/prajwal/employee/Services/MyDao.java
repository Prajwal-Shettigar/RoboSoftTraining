package com.prajwal.employee.Services;

import com.prajwal.employee.Modules.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Component
public class MyDao {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //    add record into employee
    public int insertAnEmployee(Employee employee){
        String insertStatement = "insert into employee values("+employee.getId()+",\'"+employee.getName()+"\')";
        return jdbcTemplate.update(insertStatement);
    }

    //get all records from employee
    public List<Employee> getAllEmployees(String selectStatement){

        return jdbcTemplate.query(selectStatement, (resultSet,noOfRows)->{ return new Employee(resultSet.getInt(1),resultSet.getString(2));});
    }

    //delete an employee based on id
    public int deleteEmployee(int id){
        String deleteStatement = "delete from employee where id="+id;

        return jdbcTemplate.update(deleteStatement);
    }

    //get employee details based on id
    public List<Employee> getEmployee(int id){
        String selectStatement = "select * from employee where id="+id;
        return getAllEmployees(selectStatement);
    }

    //update an employee using id
    public int updateEmployee(int id,Employee employee){
        if(getEmployee(id).isEmpty()){
            return 0;
        }
        String updateStatement = "update employee set name=\'"+employee.getName()+"\' where id="+id;

        return jdbcTemplate.update(updateStatement);

    }

}
