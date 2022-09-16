package com.prajwal.employee.Services;

import com.prajwal.employee.Modules.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;



@Repository
public class MyDao {




    JdbcTemplate jdbcTemplate;


    //creates a database uses constructor autowiring
    @Autowired
    public MyDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.execute("create table if not exists employee(id int primary key,name varchar(20))");
    }




    //    add record into employee
    public int insertAnEmployee(Employee employee){
        String insertStatement = "insert into employee values(?,?)";

        //using prepared statement updating
//        return jdbcTemplate.update(insertStatement,(preapredStatement)->{
//            preapredStatement.setInt(1,employee.getId());
//            preapredStatement.setString(2,employee.getName());
//        });

        //updating directly using values
        return jdbcTemplate.update(insertStatement,employee.getId(),employee.getName());
    }

    //get all records from employee
    public List<Employee> getAllEmployees(String selectStatement){

//        return jdbcTemplate.query(selectStatement, (resultSet,noOfRows)->{ return new Employee(resultSet.getInt(1),resultSet.getString(2));});
        return jdbcTemplate.query(selectStatement,new BeanPropertyRowMapper<>(Employee.class));

    }

    //delete an employee based on id
    public int deleteEmployee(int id){
        String deleteStatement = "delete from employee where id=?";

        return jdbcTemplate.update(deleteStatement,(preparedStatement)->{preparedStatement.setInt(1,id);});
    }

    //get employee details based on id using list
    public Employee getEmployee(int id){
        String selectStatement = "select * from employee where id="+id;
        List<Employee> employee = getAllEmployees(selectStatement);
        if(employee.isEmpty()){
            return null;
        }

        return employee.get(0);
    }

    //update an employee using id
    public int updateEmployee(int id,Employee employee){
        if(getEmployee(id)==null){
            return 0;
        }
        String updateStatement = "update employee set name=? where id=?";
//        insert into table values(?,?),(?,?);

        return jdbcTemplate.update(updateStatement,(preparedStatement)->{
            preparedStatement.setString(1,employee.getName());
            preparedStatement.setInt(2,id);
        });

    }



    //delete all employee from employee table
    public int deleteAll(){
        String deleteStatement = "delete from employee";
        return jdbcTemplate.update(deleteStatement);
    }


    //get by id returning an employee object
    public Employee getEmployeeById(int id){
        Employee employee;
        String selectStatement  = "select * from employee where id=?";
        try{
            employee =  jdbcTemplate.queryForObject(selectStatement,new BeanPropertyRowMapper<Employee>(Employee.class),id);
        }catch(DataAccessException dataAccessException){
            employee =null;
        }

        return employee;
    }



}
