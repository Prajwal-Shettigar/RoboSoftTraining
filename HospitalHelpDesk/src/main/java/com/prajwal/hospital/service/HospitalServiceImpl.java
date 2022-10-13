package com.prajwal.hospital.service;

import com.prajwal.hospital.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class HospitalServiceImpl implements HospitalService,HelpDeskService,DoctorService{



    JdbcTemplate jdbcTemplate;

    String query;

    List<String> diseases;

    @Autowired
    public HospitalServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        diseases = new ArrayList<>();

        diseases.add("Allergies");
        diseases.add("Colds and Flu");
        diseases.add("pink eye");
        diseases.add("Headaches");
        diseases.add("Mononucleosis");
        diseases.add("Stomach Aches");
    }








    //operations of hospital service
    //register a doctor
    @Override
    public Doctor registerADoctor(Doctor doctor) {
        query = "insert into doctors(name,department,max_patients,fee,room_number) values(?,?,?,?,?)";

        jdbcTemplate.update(query,(preparedStatement)->{
            preparedStatement.setString(1,doctor.getName());
            preparedStatement.setString(2,doctor.getDepartment());
            preparedStatement.setInt(3,doctor.getMaxPatients());
            preparedStatement.setDouble(4,doctor.getFee());
            preparedStatement.setInt(5,doctor.getRoom_number());
        });

        return getLastInsertedDoctor();
    }



    //add a ward
    @Override
    public Ward addAWard(Ward ward) {
        query = "insert into wards(max_patients,fee_per_day) values("+ward.getMaxPatients()+","+ward.getFeePerDay()+")";

        jdbcTemplate.update(query);

        return getLastAddedWard();
    }


    //get a list of doctors
    @Override
    public List<Doctor> getAllDocTors() {
        query = "select * from doctors";

        return getDoctors(query);
    }

    @Override
    public List<Ward> getAllWards() {
        query = "select * from wards";

        return getWards(query);
    }














    //operations of helpdesk service
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
        return getDoctors(query);

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




    //get the wards which are available
    @Override
    public List<Ward> getAvailableWards() {
        query = "select * from wards where max_patients>0";

        return getWards(query);

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






















    //operations of doctor service
    //check the patients
    @Override
    public List<MedicalRecords> checkPatients(int docId) {

        //get patients having the doctors appointment
        query = "select patient_id from appointments where doc_id="+docId;
        List<Integer> patients = jdbcTemplate.queryForList(query,Integer.class);

        if(patients.size()<1)
            return null;

        List<MedicalRecords> medicalRecords = new ArrayList<>();


        for(int patientId:patients){
            medicalRecords.add(checkPatient(patientId,docId));
        }

        return medicalRecords;

    }


    //visit all patients in a ward
    @Override
    public List<MedicalRecords> visitPatients(int docId, int wardId) {
        //get the patients in the ward
        query = "select patient_id from admit where ward_id="+wardId;
        List<Integer> patients = jdbcTemplate.queryForList(query, Integer.class);

        //if patients are not present the return
        if(patients.size()<1)
            return null;

        List<MedicalRecords> medicalRecords = new ArrayList<>();

        for(int patientId:patients){
            MedicalRecords medicalRecords1 = visitPatient(docId,wardId,patientId);
            if(medicalRecords1!=null)
                medicalRecords.add(medicalRecords1);
        }

        //if doctor has visited no patient as they were already visited return null
        if(medicalRecords.size()<1)
            return null;

        return medicalRecords;

    }


    //transfer patient
    @Override
    public Appointment transferPatient(int patientId, int fromDocId, int toDocId) {
        //check if doctor has patients appointment
        if(!checkIfAppointment(fromDocId,patientId))
            return null;

        //check if doctor is available
        query = "select count(doc_id) from appointments where doc_id="+toDocId;

        int appointments = jdbcTemplate.queryForObject(query, Integer.class);

        query = "select max_patients from doctors where id="+toDocId;

        int maxPatients = jdbcTemplate.queryForObject(query, Integer.class);

        if(appointments>=maxPatients){
            return null;
        }

        //check if to doctor has an appointment with same patient
        if(checkIfAppointment(toDocId,patientId))
            return null;

        //transfer the patient
        query = "update appointments set doc_id="+toDocId+" where doc_id="+fromDocId+" and patient_id="+patientId;
        jdbcTemplate.update(query);

        query = "select p.id,p.name,p.age,p.gender,d.id,d.name,d.department,d.room_number from patients p,doctors d where p.id="+patientId+" and d.id="+toDocId;

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












    //utility methods
    //check if doctor has particular patients appointment
    public boolean checkIfAppointment(int docId,int patientId){
        query = "select patient_id from appointments where doc_id="+docId+" and patient_id="+patientId;
        try{
            int id = jdbcTemplate.queryForObject(query, Integer.class);
        }catch(DataAccessException dataAccessException){
            return false;
        }

        return true;
    }

    public MedicalRecords visitPatient(int docId,int wardId,int patientId){

        //get the medical record for the patient in a particular ward
        query = "select * from medical_records where patient_id="+patientId+" and ward_id="+wardId;

        List<MedicalRecords> patientRecords = jdbcTemplate.query(query,(resultSet,noOfRows)->{
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


        //find a medical record of patient which has not been signed
        for(MedicalRecords medicalRecords:patientRecords){
            if(medicalRecords.getDoc_id()==0){
                query = "update medical_records set doc_id="+docId+",disease='"+pickADisease(diseases)+"' where id="+medicalRecords.getId();
                jdbcTemplate.update(query);

                //return the updated medical record
                query = "select * from medical_records where id="+medicalRecords.getId();
                return getMedicalRecord(query);
            }
        }

        return null;
    }

    //pick a random disease
    public String pickADisease(List<String> diseases){
        int randIndex = new Random().nextInt(0,diseases.size());

        return diseases.get(randIndex);
    }

    //check patient
    public MedicalRecords checkPatient(int patientId,int docId){
        //get doctor fee
        query = "select fee from doctors where id="+docId;
        double fee = jdbcTemplate.queryForObject(query,Double.class);


        //add to medical records
        query = "insert into medical_records(patient_id,doc_id,disease,type_of_patient,fee_paid) values("+patientId+","+docId+",'"+pickADisease(diseases)+"',"+2+ ","+fee+")";
        jdbcTemplate.update(query);

        //remove from appointments
        query = "delete from appointments where doc_id="+docId+" and patient_id="+patientId;
        jdbcTemplate.update(query);

        //get the medical record
        query = "select * from medical_records where id=(select max(id) from medical_records)";

        return getMedicalRecord(query);

    }


    //get the last inserted medical record
    public MedicalRecords getMedicalRecord(String query){


        return jdbcTemplate.query(query,(resultSet)->{
            if(!resultSet.next())
                return null;

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

    //get list of wards based on query
    public List<Ward> getWards(String query){
        return jdbcTemplate.query(query,(resultSet,noOfRows)->{
            Ward ward =  new Ward();

            ward.setId(resultSet.getInt(1));
            ward.setMaxPatients(resultSet.getInt(2));
            ward.setFeePerDay(resultSet.getDouble(3));

            return ward;
        });
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
    //in patient registration
    public boolean inPatientRegister(Patient patient){

        if(patient.getInsuranceId()!=0){
            return insuredInPatientRegister(patient);
        }

        return nonInsuredInPatientRegister(patient);


    }
    //out patient registration
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

    //get available beds
    public int checkBedsAvailable(int wardId){
        query = "select max_patients from wards where id="+wardId;

        return jdbcTemplate.queryForObject(query, Integer.class);
    }


    //get last added ward
    public Ward getLastAddedWard(){
        query = "select * from wards where id=(select max(id) from wards)";

        return jdbcTemplate.query(query,(resultSet)->{

            Ward ward = new Ward();
            if(!resultSet.next())
                return null;

            ward.setId(resultSet.getInt(1));
            ward.setMaxPatients(resultSet.getInt(2));
            ward.setFeePerDay(resultSet.getDouble(3));
            return ward;
        });
    }

    //get the last inserted doctor
    public Doctor getLastInsertedDoctor(){
        query = "select * from doctors where id=(select max(id) from doctors)";

        return jdbcTemplate.query(query,(resultSet)->{

            Doctor doctor  = new Doctor();
            if(!resultSet.next())
                return null;

            doctor.setId(resultSet.getInt(1));
            doctor.setName(resultSet.getString(2));
            doctor.setDepartment(resultSet.getString(3));
            doctor.setMaxPatients(resultSet.getInt(4));
            doctor.setFee(resultSet.getDouble(5));
            doctor.setRoom_number(resultSet.getInt(6));

            return doctor;
        });
    }



    //get list of doctors based on query passed
    public List<Doctor> getDoctors(String query){
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
}
