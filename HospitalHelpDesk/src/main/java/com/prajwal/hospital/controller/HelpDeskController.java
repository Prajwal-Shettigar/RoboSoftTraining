package com.prajwal.hospital.controller;


import com.prajwal.hospital.model.Appointment;
import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.Patient;
import com.prajwal.hospital.model.User;
import com.prajwal.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/HelpDesk")
public class HelpDeskController {


    @Autowired
    HospitalService hospitalService;



    //registration for in patients
    @PostMapping("/Register/In")
    public ResponseEntity<Patient> registerInPatient(@RequestBody User user){

        Patient patient1 = hospitalService.getHelpDeskService().registerPatient(new Patient(user.getName(),user.getAge(),user.getGender(),"in_patient",user.getTelNumber()));

        if(patient1!=null){
            return ResponseEntity.ok(patient1);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    //registration for out patients
    @PostMapping("/Register/Out/{noOfDays}")
    public ResponseEntity<Patient> registerOutPatient(@RequestBody User user, @PathVariable int noOfDays){

        Patient patient1 = hospitalService.getHelpDeskService().registerPatient(new Patient(user.getName(),user.getAge(),user.getGender(),"out_patient",user.getTelNumber(),user.getInsuranceId(),noOfDays));

        if(patient1!=null){
            return ResponseEntity.ok(patient1);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    //get doctors by name
    @GetMapping("/Doctors/{name}")
    public ResponseEntity<List<Doctor>> getDoctorsByName(@PathVariable String name){
        List<Doctor> doctors  = hospitalService.getHelpDeskService().getDoctorsByName(name);

        if(doctors.size()>0){
            return ResponseEntity.ok(doctors);
        }

        return ResponseEntity.noContent().build();
    }

    //book an appointment
    @PostMapping("/Book/{docId}/{patientId}")
    public ResponseEntity<Appointment> bookAnAppointment(@PathVariable int docId,@PathVariable int patientId){
        Appointment appointment = hospitalService.getHelpDeskService().bookAnAppointment(docId,patientId);

        if(appointment!=null){
            return ResponseEntity.ok(appointment);
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }


}
