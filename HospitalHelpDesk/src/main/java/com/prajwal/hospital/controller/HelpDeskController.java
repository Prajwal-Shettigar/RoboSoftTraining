package com.prajwal.hospital.controller;


import com.prajwal.hospital.model.*;
import com.prajwal.hospital.service.HelpDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Hospital/HelpDesk")
public class HelpDeskController {


    @Autowired
    HelpDeskService helpDeskService;



    //registration for out patients
    @PostMapping("/Register/Out")
    public ResponseEntity<Patient> registerInPatient(@RequestBody User user){

        Patient patient1 = helpDeskService.registerPatient(new Patient(user.getName(),user.getAge(),user.getGender(),"out_patient",user.getTelNumber()));

        if(patient1!=null){
            return ResponseEntity.ok(patient1);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //registration for in patients
    @PostMapping("/Register/In/{noOfDays}")
    public ResponseEntity<Patient> registerInPatient(@RequestBody User user, @PathVariable int noOfDays){

        Patient patient1 = helpDeskService.registerPatient(new Patient(user.getName(),user.getAge(),user.getGender(),"in_patient",user.getTelNumber(),user.getInsuranceId(),noOfDays));

        if(patient1!=null){
            return ResponseEntity.ok(patient1);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    //get doctors by name
    @GetMapping("/Doctors/{name}")
    public ResponseEntity<List<Doctor>> getDoctorsByName(@PathVariable String name){
        List<Doctor> doctors  = helpDeskService.getDoctorsByName(name);

        if(doctors.size()>0){
            return ResponseEntity.ok(doctors);
        }

        return ResponseEntity.noContent().build();
    }

    //book an appointment
    @PostMapping("/Book/{docId}/{patientId}")
    public ResponseEntity<Appointment> bookAnAppointment(@PathVariable int docId,@PathVariable int patientId){
        Appointment appointment = helpDeskService.bookAnAppointment(docId,patientId);

        if(appointment!=null){
            return ResponseEntity.ok(appointment);
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //get available wards
    @GetMapping("/Wards")
    public ResponseEntity<List<Ward>> getAvailableWards(){
        List<Ward> wards = helpDeskService.getAvailableWards();

        if(wards.size()>0){
            return ResponseEntity.ok(wards);
        }

        return ResponseEntity.noContent().build();
    }

    //admit a patient
    @PostMapping("/Admit/{wardId}/{patientId}")
    public ResponseEntity<Admission> admitAPatient(@PathVariable int wardId,@PathVariable int patientId){
        Admission admission = helpDeskService.admitAPatient(wardId,patientId);

        if(admission!=null)
            return ResponseEntity.ok(admission);

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

    //get medical records by id
    @GetMapping("/Records/{patientId}")
    public ResponseEntity<List<MedicalRecords>> getMedicalRecordById(@PathVariable int patientId){
        List<MedicalRecords> medicalRecords = helpDeskService.getMedicalRecordsById(patientId);

        if(medicalRecords.size()>0)
            return ResponseEntity.ok(medicalRecords);

        return ResponseEntity.noContent().build();
    }




}
