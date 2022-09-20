import java.util.ArrayList;
import java.util.List;

public class Department {

    private String name;
    private List<Doctor> doctors;



    //constructor
    public Department(String name) {
        this.name = name;
        doctors = new ArrayList<>();
    }


    //getter
    public String getName() {
        return name;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }


    //check if doctor exists using the name of the doctor if not then return null
    public Doctor doesDoctorExists(String doctorName){
        for(Doctor doctor:doctors){
            if(doctor.getName().equalsIgnoreCase(doctorName))
                return doctor;
        }

        return null;
    }
}
