package com.prajwal.insurance.controller;


import com.prajwal.insurance.model.Accident;
import com.prajwal.insurance.model.Car;
import com.prajwal.insurance.model.Participated;
import com.prajwal.insurance.model.Person;
import com.prajwal.insurance.service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

    //given queries
    //count accident by year
    @GetMapping("/Insurance/count/year/{year}")
    public int getCarOwnerCountInAccidentByYear(@PathVariable int year){
        return insuranceService.getCarOwnerCountInAccidentByYear(year);
    }

    //count accident by name
    @GetMapping("/Insurance/count/name/{name}")
    public int getCarOwnerCountInAccidentByName(@PathVariable String name){
        return insuranceService.getCarOwnerCountInAccidentByName(name);
    }

    //update fine of participant
    @PatchMapping("/Insurance")
    public int updateParticipated(@RequestBody Participated participated) {
        return insuranceService.updateParticipated(participated);
    }


    //insert operation
    //register a driver
    @PutMapping("/Insurance/Driver")
    public int registerADriver(@RequestBody Person person){
        return insuranceService.addDriver(person);
    }

    //register a car
    @PutMapping("/Insurance/Car/{driverId}")
    public int registerACar(@PathVariable String driverId,@RequestBody Car car){
        return insuranceService.registerACar(driverId,car);
    }

    //report an accident
    @PutMapping("/Insurance/Accident")
    public int reportAccident(@RequestBody Accident accident){
        return insuranceService.reportAnAccident(accident);
    }

    //get operation
    //get all drivers
    @GetMapping("/Drivers")
    public List<Person> getAllDrivers(){
        return insuranceService.getAllDrivers();
    }

    //get all cars
    @GetMapping("/Cars")
    public List<Car> getAllCars(){
        return insuranceService.getAllCars();
    }

    //get all accidents
    @GetMapping("/Accidents")
    public List<Accident> getAllAccidents(){
        return insuranceService.getAllAccidents();
    }

    //delete operation
    //delete driver based on id
    @DeleteMapping("/Driver/{driverId}")
    public int deleteDriverById(@PathVariable String driverId){
        return insuranceService.deleteDriver(driverId);
    }

    //delete car based on reg no
    @DeleteMapping("/Car/{regNo}")
    public int deleteCarById(@PathVariable String regNo){
        return insuranceService.deleteCar(regNo);
    }

    //delete a accident based on report number
    @DeleteMapping("/Accident/{reportNumber}")
    public int deleteAccidentById(@PathVariable int reportNumber){
        return insuranceService.deleteAccident(reportNumber);
    }
}
