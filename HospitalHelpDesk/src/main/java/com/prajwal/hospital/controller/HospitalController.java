package com.prajwal.hospital.controller;

import com.prajwal.hospital.model.Doctor;
import com.prajwal.hospital.model.Ward;
import com.prajwal.hospital.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    //get all doctors
    @GetMapping("/Doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors(){
        List<Doctor> doctors = hospitalService.getAllDocTors();

        if(doctors.size()>0)
            return ResponseEntity.ok(doctors);

        return ResponseEntity.noContent().build();
    }


    //get all wards
    @GetMapping("/Wards")
    public ResponseEntity<List<Ward>> getAllWards(){
        List<Ward> wards = hospitalService.getAllWards();

        if(wards.size()>0)
            return ResponseEntity.ok(wards);

        return ResponseEntity.noContent().build();
    }

}
