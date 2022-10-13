package com.prajwal.hospital.service;


import com.prajwal.hospital.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

public class HelpDeskServiceImpl implements HelpDeskService{



    JdbcTemplate jdbcTemplate;
    String query;



    HelpDeskServiceImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    //register a patient
    @Override
    public Patient registerPatient(Patient patient) {

        if(patient.getType().equalsIgnoreCase("in_patient")){
            if (!inPatientRegister(patient))
                return null;
        }else if(patient.getType().equalsIgnoreCase("out_patient")){
            if(!outPatientRegister(patient))
                return null;
        }else{
            return null;
        }

        return getLastInsertedPatient();

    }




    //get doctors by name
    @Override
    public List<Doctor> getDoctorsByName(String name){
        query = "select * from doctors where name ='"+name+"'";

        return jdbcTemplate.query(query,(resultSet,noOfRows)->{
            Doctor doctor = new Doctor();

            doctor.setId(resultSet.getInt(1));
            doctor.setName(resultSet.getString(2));
            doctor.setDepartment(resultSet.getString(3));
            doctor.setMaxPatients(resultSet.getInt(4));
            doctor.setFee(resultSet.getDouble(5));
            doctor.setRoom_number(resultSet.getInt(6));

            return doctor;
        });
    }


    //book an appointment
    @Override
    public Appointment bookAnAppointment(int docId, int patientId) {
        query = "select count(doc_id) from appointments where doc_id="+docId;

        int appointments = jdbcTemplate.queryForObject(query, Integer.class);

        query = "select max_patients from doctors where id="+docId;

        int maxPatients = jdbcTemplate.queryForObject(query, Integer.class);

        if(appointments>=maxPatients){
            return null;
        }

        query = "insert into appointments values("+docId+","+patientId+")";
        jdbcTemplate.update(query);

        query = "select p.id,p.name,p.age,p.gender,d.id,d.name,d.department,d.room_number from patients p,doctors d where p.id="+patientId+" and d.id="+docId;

        return jdbcTemplate.query(query,(resultSet)->{

            Appointment appointment = new Appointment();
            if(!resultSet.next())
                return null;
            appointment.setPatient_id(resultSet.getInt(1));
            appointment.setPatient_name(resultSet.getString(2));
            appointment.setAge(resultSet.getInt(3));
            appointment.setGender(resultSet.getString(4));
            appointment.setDoc_id(resultSet.getInt(5));
            appointment.setDoc_name(resultSet.getString(6));
            appointment.setDepartment(resultSet.getString(7));
            appointment.setRoom_number(resultSet.getInt(8));
            return appointment;
        });


    }


    //admit an in patient
    @Override
    public Admission admitAPatient(int wardId, int patientId) {

        //check if ward has beds available
        int beds = checkBedsAvailable(wardId);

        if(beds<1)
            return null;

        //get the number of days to be admitted
        query = "select no_of_days from patients where id="+patientId;
        int noOfDays = jdbcTemplate.queryForObject(query, Integer.class);

        if(noOfDays<1)
            return null;

        //admit the patient
        query = "insert into admit values("+wardId+","+patientId+")";

        jdbcTemplate.update(query);

        //decrement the bed count
        query = "update wards set max_patients="+(beds-1)+" where id="+wardId;
        jdbcTemplate.update(query);

        //get fee per day
        query = "select fee_per_day from wards where id="+wardId;
        double feePerDay = jdbcTemplate.queryForObject(query, Double.class);


        double totalFee = noOfDays*feePerDay;
        //claim insurance

        double afterClaim = claimInsurance(patientId,totalFee);




        //add to med records
        query  = "insert into medical_records(patient_id,ward_id,type_of_patient,fee_paid,days_admitted) values("+patientId+","+wardId+","+1+","+totalFee+","+noOfDays+")";
        jdbcTemplate.update(query);

        return new Admission(patientId,wardId,noOfDays,feePerDay,totalFee,afterClaim);


    }

    //claim insurance
    private double claimInsurance(int patientId,double totalFee){

        //get insurance id
        int insurance_id =0;
        try{
            query = "select ins_id from patients where id="+patientId;
            insurance_id= jdbcTemplate.queryForObject(query, Integer.class);
        }catch (NullPointerException nullPointerException){
            System.out.println("patient has no insurance...");
        }

        if(insurance_id<1)
            return totalFee;

        //get the claim amount
        query = "select claim_amount from insurance where id="+insurance_id;
        double claim_amount = jdbcTemplate.queryForObject(query, Double.class);

        if(claim_amount>totalFee)
            return 0;

        return totalFee-claim_amount;
    }



    //get the wards which are available
    @Override
    public List<Ward> getAvailableWards() {
        query = "select * from wards where max_patients>0";

        return jdbcTemplate.query(query,(resultSet,noOfRows)->{
            Ward ward =  new Ward();

            ward.setId(resultSet.getInt(1));
            ward.setMaxPatients(resultSet.getInt(2));
            ward.setFeePerDay(resultSet.getDouble(3));

            return ward;
        });
    }

    @Override
    public List<MedicalRecords> getMedicalRecordsById(int patientId) {
        query = "select * from medical_records where patient_id="+patientId;

        return jdbcTemplate.query(query,(resultSet,noOfRows)->{
            MedicalRecords medicalRecords = new MedicalRecords();

            medicalRecords.setId(resultSet.getInt(1));
            medicalRecords.setPatient_id(resultSet.getInt(2));
            medicalRecords.setDoc_id(resultSet.getInt(3));
            medicalRecords.setWard_id(resultSet.getInt(4));
            medicalRecords.setDateOfIssue(resultSet.getTimestamp(5));
            medicalRecords.setDisease(resultSet.getString(6));
            medicalRecords.setTypeOfPatient(resultSet.getString(7));
            medicalRecords.setFeePaid(resultSet.getDouble(8));
            medicalRecords.setDaysAdmitted(resultSet.getInt(9));

            return medicalRecords;
        });
    }


    //get available beds
    public int checkBedsAvailable(int wardId){
        query = "select max_patients from wards where id="+wardId;

        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    //check for insurance using insurance id and name if not present the return null else return insurance data
    public Insurance checkForInsurance(int insuranceId,String name){
        query = "select * from insurance where id=? and name=?";

        return jdbcTemplate.query(query,(preparedStatement)->{
            preparedStatement.setInt(1,insuranceId);
            preparedStatement.setString(2,name);
        },(resultSet)->{
            Insurance insurance = new Insurance();
            if(!resultSet.next())
                return null;
            insurance.setId(resultSet.getInt(1));
            insurance.setName(resultSet.getString(2));
            insurance.setAge(resultSet.getInt(3));
            insurance.setGender(resultSet.getString(4));
            insurance.setClaimAmount(resultSet.getDouble(5));
            return insurance;

        });

    }


    //in patient registration
    public boolean outPatientRegister(Patient patient){
        query = "insert into patients(name,age,gender,type,tel_number) values(?,?,?,?,?)";

        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,patient.getName());
            preparedStatement.setInt(2,patient.getAge());
            preparedStatement.setString(3,patient.getGender());
            preparedStatement.setInt(4,2);
            preparedStatement.setBigDecimal(5,patient.getTelNumber());
        });

        return true;

    }


    //out patient registration
    public boolean inPatientRegister(Patient patient){

        if(patient.getInsuranceId()!=0){
            return insuredInPatientRegister(patient);
        }

        return nonInsuredInPatientRegister(patient);


    }

    // insured patient registration
    public boolean insuredInPatientRegister(Patient patient){

        if(checkForInsurance(patient.getInsuranceId(),patient.getName())==null){
            return false;
        }

        query = "insert into patients(name,age,gender,type,tel_number,ins_id,no_of_days) values(?,?,?,?,?,?,?)";

        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,patient.getName());
            preparedStatement.setInt(2,patient.getAge());
            preparedStatement.setString(3,patient.getGender());
            preparedStatement.setInt(4,1);
            preparedStatement.setBigDecimal(5,patient.getTelNumber());
            preparedStatement.setInt(6,patient.getInsuranceId());
            preparedStatement.setInt(7,patient.getNumberOfDays());
        });

        return true;


    }

    // non insured patient registration
    public boolean nonInsuredInPatientRegister(Patient patient){
        query = "insert into patients(name,age,gender,type,tel_number,no_of_days) values(?,?,?,?,?,?)";

        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,patient.getName());
            preparedStatement.setInt(2,patient.getAge());
            preparedStatement.setString(3,patient.getGender());
            preparedStatement.setInt(4,1);
            preparedStatement.setBigDecimal(5,patient.getTelNumber());
            preparedStatement.setInt(6,patient.getNumberOfDays());
        });

        return true;


    }






    //get last inserted patient
    public Patient getLastInsertedPatient(){
        query = "select * from patients where id=(select max(id) from patients)";

        return jdbcTemplate.query(query,(resultSet)->{

            Patient patient  = new Patient();
            if(!resultSet.next())
                return null;

            patient.setId(resultSet.getInt(1));
            patient.setName(resultSet.getString(2));
            patient.setAge(resultSet.getInt(3));
            patient.setGender(resultSet.getString(4));
            patient.setType(resultSet.getString(5));
            patient.setTelNumber(resultSet.getBigDecimal(6));
            patient.setInsuranceId(resultSet.getInt(7));
            patient.setNumberOfDays(resultSet.getInt(8));

            return patient;
        });
    }





}
