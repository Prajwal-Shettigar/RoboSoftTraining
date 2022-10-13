package com.prajwal.hospital.controller;


import com.prajwal.hospital.model.MedicalRecords;
import com.prajwal.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Doctor")
public class DoctorController {

    @Autowired
    HospitalService hospitalService;

    //check patients under appointment
    @GetMapping("/Check/{docId}")
    public ResponseEntity<List<MedicalRecords>> checkPatients(@PathVariable int docId){
        List<MedicalRecords> medicalRecords = hospitalService.getDoctorService().checkPatients(docId);

        if(medicalRecords!=null)
            return ResponseEntity.ok(medicalRecords);

        return ResponseEntity.noContent().build();
    }

    //visit a patient
    @GetMapping("/Visit/{docId}/{wardId}")
    public ResponseEntity<List<MedicalRecords>> visitPatients(@PathVariable int docId,@PathVariable int wardId){
        List<MedicalRecords> medicalRecords = hospitalService.getDoctorService().visitPatients(docId,wardId);

        if(medicalRecords!=null)
            return ResponseEntity.ok(medicalRecords);

        return ResponseEntity.noContent().build();
    }

    //transfer a patient
    
}
