import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelpDesk {

    Hospital hospital;



    //an in patient has been created and added to hospital database with id given by hospital and also create a space to store patient records
    public Patient registerAnInPatient(String patientName, int age, String gender, long telephoneNumber){
        int patientId = hospital.getPatientId();
        Patient patient = new Patient(patientId,patientName,age,gender,telephoneNumber);

        hospital.getPatients().put(patientId,patient);
        hospital.getPatientRecords().put(patientId,new ArrayList<>());

        return patient;
    }


    //book an appointment
    public String bookAnAppointMent(int patientId,String doctorName,String department){
        Patient  patient = hospital.getPatientById(patientId);

        if(patient==null)
            return "invalid patient id ...";

        Doctor doctor = hospital.getDoctorByName(doctorName,department);

        if(doctor==null)
            return "invalid doctor name";

        if(doctor.areMaxPatients())
            return "doctor not available for today...";

        doctor.placeanAppo8intment(patient);

        return "appointment placed successfully wait at "+doctor.getLocation();
    }


    //get medical records by id
    public List<Record> getMedicalRecordsById(int patientId){
        return hospital.getPatientRecords().get(patientId);
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    //get all doctors along with their department
    public void getAllDoctorsWIthDepartment(){
        Map<Integer,Doctor> doctors = hospital.getDoctors();

        if(doctors.size()<1){
            System.out.println("Currenlt no doctors available..");
            return;
        }

        for(Doctor doctor: doctors.values()){
            System.out.println("Name : "+doctor.getDoctorName()+"\t Department : "+doctor.getDepartment());
        }
    }


}
