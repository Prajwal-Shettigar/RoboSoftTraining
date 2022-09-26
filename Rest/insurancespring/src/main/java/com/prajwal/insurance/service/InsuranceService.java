package com.prajwal.insurance.service;


import com.prajwal.insurance.model.Accident;
import com.prajwal.insurance.model.Car;
import com.prajwal.insurance.model.Participated;
import com.prajwal.insurance.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class InsuranceService {


    @Autowired
    JdbcTemplate jdbcTemplate;

    //get total number of car owners who were involved in accidents in a particular year
    public int getCarOwnerCountInAccidentByYear(int year){
        String selectQuery = "select count(distinct driver_id)  from participated inner join accident on participated.report_number=accident.report_number where accd_date like \""+year+"%\"";
        return jdbcTemplate.query(selectQuery,(resultSetExtractor)->{
            resultSetExtractor.next();
            return resultSetExtractor.getInt(1);
        });
    }

    //get total number of people involved in a accident of a particular name
    public int getCarOwnerCountInAccidentByName(String name){
        String selectQuery = "select count(*) from participated inner join person on participated.driver_id=person.driver_id where name=\""+name+"\"";
        return jdbcTemplate.query(selectQuery,(resultSetExtractor)->{
            resultSetExtractor.next();
            return resultSetExtractor.getInt(1);
        });
    }


    //update the fine amount based on reg number and report number
    public int updateParticipated(Participated participated){
        String updateQuery = "update participated set damage_amount="+participated.getDamageAmount()+" where regno=\""+participated.getRegNo()+"\" and report_number="+participated.getReportNumber();
        return jdbcTemplate.update(updateQuery);
    }


    //register a driver
    public int addDriver(Person person){
        String insertQuery = "insert into person values(?,?,?)";
        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setString(1,person.getDriverId());
            preparedStatement.setString(2,person.getName());
            preparedStatement.setString(3, person.getAddress());
        });
    }

    //register a car
    public int registerACar(String driverId, Car car){
        String insertIntoCarQuery = "insert into car values(?,?,?)";
        String insertIntoOwnsQuery = "insert into owns values(?,?)";

        //insert into car
        jdbcTemplate.update(insertIntoCarQuery,(preparedStatement)->
        {
            preparedStatement.setString(1,car.getRegNo());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setInt(3,car.getYear());
        });

        //insert into owns
        return jdbcTemplate.update(insertIntoOwnsQuery,(preparedStatement)->{
            preparedStatement.setString(1,driverId);
            preparedStatement.setString(2,car.getRegNo());
        });
    }

    //report an accident
    public int reportAnAccident(Accident accident){
        String insertIntoAccidentQuery = "insert into accident values(?,?,?)";
        String insertIntoParticipatedQuery = "insert into participated values(?,?,?,?)";


        //insert into accident
        jdbcTemplate.update(insertIntoAccidentQuery,(preparedStatement)->{
            preparedStatement.setInt(1,accident.getReportNumber());
            preparedStatement.setDate(2,accident.getAccidentDate());
            preparedStatement.setString(3,accident.getLocation());
        });
        //insert into participated
        for(Participated participated:accident.getParticipents()){
            jdbcTemplate.update(insertIntoParticipatedQuery,(preparedStatement)->{
                preparedStatement.setString(1,participated.getDriverId());
                preparedStatement.setString(2,participated.getRegNo());
                preparedStatement.setInt(3,accident.getReportNumber());
                preparedStatement.setInt(4,participated.getDamageAmount());
            });
        }

        return accident.getParticipents().size();

    }

    //get all driver details
    public List<Person> getAllDrivers(){
        return jdbcTemplate.query("select * from person",new BeanPropertyRowMapper<Person>(Person.class));
    }

    //get all car details
    public List<Car> getAllCars(){
        return jdbcTemplate.query("select * from car",new BeanPropertyRowMapper<Car>(Car.class));
    }

    //get all accident details
    public List<Accident> getAllAccidents(){
        return jdbcTemplate.query("select * from accident", new RowMapper<Accident>() {
            @Override
            public Accident mapRow(ResultSet rs, int rowNum) throws SQLException {
                Accident accident = new Accident();
                accident.setReportNumber(rs.getInt(1));
                accident.setAccidentDate(rs.getDate(2));
                accident.setLocation(rs.getString(3));

                accident.setParticipents(jdbcTemplate.query("select * from participated where report_number="+accident.getReportNumber(),new BeanPropertyRowMapper<Participated>(Participated.class)));
                return accident;
            }
        });
    }

    //get driver details based on driver id
    public Person getDriverById(String driverId){
        return jdbcTemplate.queryForObject("select * from person where driver_id='"+driverId+"'", Person.class);
    }

    //get car details based on reg number
    public Car getCarById(String regNo){
        return jdbcTemplate.queryForObject("select * from car where regno='"+regNo+"'", Car.class);
    }

    //get accident details based on report number
    public Accident getAccidentById(int reportNumber){
        return jdbcTemplate.query("select * from accident where report_number="+reportNumber,(resultSet)->{
            resultSet.next();
            Accident accident = new Accident();
            accident.setReportNumber(resultSet.getInt(1));
            accident.setAccidentDate(resultSet.getDate(2));
            accident.setLocation(resultSet.getString(3));
            accident.setParticipents(jdbcTemplate.query(""));
            return accident;

        });
    }


    //delete records of driver
    public int deleteDriver(String driverId){
        String deleteFromParticipatedQuery = "delete from participated where driver_id='"+driverId+"'";
        String deleteFromOwns = "delete from owns where driver_id='"+driverId+"'";
        String deleteFromPerson = "delete from person where driver_id='"+driverId+"'";

        jdbcTemplate.update(deleteFromParticipatedQuery);
        jdbcTemplate.update(deleteFromOwns);
        return jdbcTemplate.update(deleteFromPerson);
    }

    //delete records of car
    public int deleteCar(String regNo){
        String deleteFromParticipatedQuery = "delete from participated where regno='"+regNo+"'";
        String deleteFromOwns = "delete from owns where reg_no='"+regNo+"'";
        String deleteFromPerson = "delete from car where regno='"+regNo+"'";

        jdbcTemplate.update(deleteFromParticipatedQuery);
        jdbcTemplate.update(deleteFromOwns);
        return jdbcTemplate.update(deleteFromPerson);

    }

    //delete records of accident
    public int deleteAccident(int reportNumber){
        String deleteFromParticipated = "delete from participated where report_number="+reportNumber;
        String deleteFromAccident = "delete from accident where report_number="+reportNumber;

        jdbcTemplate.update(deleteFromParticipated);
        return jdbcTemplate.update(deleteFromAccident);
    }


}
