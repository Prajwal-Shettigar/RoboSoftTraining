package com.prajwal.hospital.controller;


import com.prajwal.hospital.model.Appointment;
import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.MedicalRecords;
import com.prajwal.hospital.service.DoctorService;
import com.prajwal.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Hospital/Doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    //check patients under appointment
    @GetMapping("/Check/{docId}")
    public ResponseEntity<List<MedicalRecords>> checkPatients(@PathVariable int docId){
        List<MedicalRecords> medicalRecords = doctorService.checkPatients(docId);

        if(medicalRecords!=null)
            return ResponseEntity.ok(medicalRecords);

        return ResponseEntity.noContent().build();
    }

    //visit a patient
    @GetMapping("/Visit/{docId}/{wardId}")
    public ResponseEntity<List<MedicalRecords>> visitPatients(@PathVariable int docId,@PathVariable int wardId){
        List<MedicalRecords> medicalRecords = doctorService.visitPatients(docId,wardId);

        if(medicalRecords!=null)
            return ResponseEntity.ok(medicalRecords);

        return ResponseEntity.noContent().build();
    }

    //transfer a patient
    @PatchMapping("/Transfer/{fromDocId}/{toDocId}/{patientId}")
    public ResponseEntity<Appointment> transferPatient(@PathVariable int fromDocId,@PathVariable int toDocId,@PathVariable int patientId){
        Appointment appointment = doctorService.transferPatient(patientId,fromDocId,toDocId);

        if(appointment!=null)
            return ResponseEntity.ok(appointment);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
    }

}
