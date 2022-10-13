package com.prajwal.hospital.service;

import com.prajwal.hospital.model.MedicalRecords;

import java.util.List;

public interface DoctorService {

    //check patients
    List<MedicalRecords> checkPatients(int docId);



    //visit wards
    List<MedicalRecords> visitPatients(int docId,int wardId);
}
