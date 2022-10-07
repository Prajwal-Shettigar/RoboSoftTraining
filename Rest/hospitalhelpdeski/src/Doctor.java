import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Doctor {

    private int doctorId;
    private String doctorName;

    private String department;

    private String location;

    private static final int maxPatients = 4;

    private List<Patient> appointments;

    //constructor
    public Doctor(int doctorId, String doctorName, String department, String location) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.department = department;
        this.location = location;

        appointments = new ArrayList<>();
    }

    //are max patients
    public boolean areMaxPatients(){
        return appointments.size()>=maxPatients;
    }

    //place an appointment
    public void placeanAppo8intment(Patient patient){
        appointments.add(patient);
    }


    //getters and setters
    public String getLocation() {
        return location;
    }

    public String getDoctorName() {
        return doctorName;
    }


    public String getDepartment() {
        return department;
    }


}
