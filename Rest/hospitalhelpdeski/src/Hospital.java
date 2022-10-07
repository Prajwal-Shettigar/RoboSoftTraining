import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hospital {

    private String hospitalName;
    private HelpDesk helpDesk;

    Map<Integer,Patient> patients;

    Map<Integer, List<Record>> patientRecords;

    Map<Integer,Doctor> doctors;


    public Hospital(String hospitalName) {
        this.hospitalName = hospitalName;

        patients = new HashMap<>();
        patientRecords= new HashMap<>();
        doctors = new HashMap<>();
    }

    private int patientCount = 0;
    private int doctorCount = 0;




    //get a new patient id every time
    public int getPatientId(){

        patientCount+=1;
        return patientCount;
    }

    //get a new doctor id every time
    public int getDoctorId(){
        doctorCount +=1;
        return doctorCount;
    }


    //get patient records by id
    public List<Record> getPatientRecordById(int patientId){
        return patientRecords.get(patientId);
    }

    //get doctors by name
    public Doctor getDoctorByName(String doctorName,String deparment){

        for(Doctor doctor : doctors.values()){
            if((doctor.getDoctorName().equalsIgnoreCase(doctorName)) && (doctor.getDoctorName().equalsIgnoreCase(deparment))){
                return doctor;
            }
        }

        return null;
    }

    //get patient by id
    public Patient getPatientById(int patientId){
        return patients.get(patientId);
    }

    //add doctor
    public void addDoctor(String doctorName, String department, String location){
        int docId = getDoctorId();
        doctors.put(docId,new Doctor(docId,doctorName,department,location));
    }











    //gettters and setters

    public Map<Integer, Patient> getPatients() {
        return patients;
    }

    public Map<Integer, List<Record>> getPatientRecords() {
        return patientRecords;
    }

    public void setHelpDesk(HelpDesk helpDesk) {
        this.helpDesk = helpDesk;
        helpDesk.setHospital(this);
    }

    public HelpDesk getHelpDesk() {
        return helpDesk;
    }

    public Map<Integer, Doctor> getDoctors() {
        return doctors;
    }
}
