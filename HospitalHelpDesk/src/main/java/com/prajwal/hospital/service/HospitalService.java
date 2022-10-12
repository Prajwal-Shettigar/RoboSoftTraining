package com.prajwal.hospital.service;

import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.Ward;

public interface HospitalService {


    //add doctors
    Doctor registerADoctor(Doctor doctor);


    //get the helpdesk
    HelpDeskService getHelpDeskService();


    //add a ward
    Ward addAWard(Ward ward);
}
