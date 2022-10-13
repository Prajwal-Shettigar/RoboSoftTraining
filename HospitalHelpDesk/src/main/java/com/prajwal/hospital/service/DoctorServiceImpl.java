package com.prajwal.hospital.service;

import com.prajwal.hospital.model.MedicalRecords;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoctorServiceImpl implements DoctorService{

    JdbcTemplate jdbcTemplate;

    List<String> diseases;

    String query;

    public DoctorServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        diseases = new ArrayList<>();

        diseases.add("Allergies");
        diseases.add("Colds and Flu");
        diseases.add("pink eye");
        diseases.add("Headaches");
        diseases.add("Mononucleosis");
        diseases.add("Stomach Aches");
    }


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
}
