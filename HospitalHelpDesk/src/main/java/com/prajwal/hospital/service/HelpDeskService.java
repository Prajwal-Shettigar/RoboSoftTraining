package com.prajwal.hospital.service;

import com.prajwal.hospital.model.*;

import java.util.List;

public interface HelpDeskService {

    //register patient
    Patient registerPatient(Patient patient);



    //get doctors by name
    List<Doctor> getDoctorsByName(String name);


    //book an appointment
    Appointment bookAnAppointment(int docId, int patientId);

    //admit a patient
    Admission admitAPatient(int wardId,int patientId);


    //get available wards
    List<Ward> getAvailableWards();

    //get medical records based on patient id
    List<MedicalRecords> getMedicalRecordsById(int patientId);

    //get all doctors


}
