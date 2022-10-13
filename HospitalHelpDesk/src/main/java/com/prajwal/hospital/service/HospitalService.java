package com.prajwal.hospital.service;

import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.Ward;

import java.util.List;

public interface HospitalService {


    //add doctors
    Doctor registerADoctor(Doctor doctor);


    //add a ward
    Ward addAWard(Ward ward);


    //get all doctors
    List<Doctor> getAllDocTors();

    //get all wards
    List<Ward> getAllWards();

}
