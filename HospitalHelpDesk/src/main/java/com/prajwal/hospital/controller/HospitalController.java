package com.prajwal.hospital.controller;

import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.Ward;
import com.prajwal.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Hospital")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    //register a doctor
    @PostMapping("/Doctors/Register")
    public ResponseEntity<Doctor> registerADoctor(@RequestBody Doctor doctor){
        return ResponseEntity.ok(hospitalService.registerADoctor(doctor));
    }

    //add a ward
    @PostMapping("/Ward/Add")
    public ResponseEntity<Ward> addAWard(@RequestBody Ward ward){
        return ResponseEntity.ok(hospitalService.addAWard(ward));
    }

}
