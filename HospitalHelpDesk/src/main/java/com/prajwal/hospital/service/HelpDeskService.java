package com.prajwal.hospital.service;

import com.prajwal.hospital.model.Appointment;
import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.Patient;

import java.util.List;

public interface HelpDeskService {

    //register patient
    Patient registerPatient(Patient patient);



    //get doctors by name
    List<Doctor> getDoctorsByName(String name);


    //book an appointment
    Appointment bookAnAppointment(int docId, int patientId);

    //admit a patient

}
