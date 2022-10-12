package com.prajwal.hospital.service;

import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.Ward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class HospitalServiceImpl implements HospitalService{



    JdbcTemplate jdbcTemplate;
    HelpDeskService helpDeskService;


    @Autowired
    HospitalServiceImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        helpDeskService = new HelpDeskServiceImpl(jdbcTemplate);
    }

    String query;

    //register a doctor
    @Override
    public Doctor registerADoctor(Doctor doctor) {
        query = "insert into doctors(name,department,max_patients,fee,room_number) values(?,?,?,?,?)";

        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,doctor.getName());
            preparedStatement.setString(2,doctor.getDepartment());
            preparedStatement.setInt(3,doctor.getMaxPatients());
            preparedStatement.setDouble(4,doctor.getFee());
            preparedStatement.setInt(5,doctor.getRoom_number());
        });

        return getLastInsertedDoctor();
    }


    //get helpdesk service
    @Override
    public HelpDeskService getHelpDeskService() {
        return helpDeskService;
    }



    //add a ward
    @Override
    public Ward addAWard(Ward ward) {
        query = "insert into wards(max_patients,fee_per_day) values("+ward.getMaxPatients()+","+ward.getFeePerDay()+")";

        jdbcTemplate.update(query);

        return getLastAddedWard();
    }

    //get last added ward
    public Ward getLastAddedWard(){
        query = "select * from wards where id=(select max(id) from wards)";

        return jdbcTemplate.query(query,(resultSet)->{

            Ward ward = new Ward();
            if(!resultSet.next())
                return null;

            ward.setId(resultSet.getInt(1));
            ward.setMaxPatients(resultSet.getInt(2));
            ward.setFeePerDay(resultSet.getDouble(3));
            return ward;
        });
    }

    //get the last inserted doctor
    public Doctor getLastInsertedDoctor(){
        query = "select * from doctors where id=(select max(id) from doctors)";

        return jdbcTemplate.query(query,(resultSet)->{

            Doctor doctor  = new Doctor();
            if(!resultSet.next())
                return null;

            doctor.setId(resultSet.getInt(1));
            doctor.setName(resultSet.getString(2));
            doctor.setDepartment(resultSet.getString(3));
            doctor.setMaxPatients(resultSet.getInt(4));
            doctor.setFee(resultSet.getDouble(5));
            doctor.setRoom_number(resultSet.getInt(6));

            return doctor;
        });
    }


}
