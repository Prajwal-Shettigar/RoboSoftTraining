import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor {

    private int doctorId;
    private String name;
    private static final int maxPatientsAllocated = 3;
    private List<Patient> patients;

    private double doctorsFee;

    private Room room;



    //constructor
    public Doctor(int doctorId, String name, double doctorsFee) {
        this.doctorId = doctorId;
        this.name = name;
        this.doctorsFee = doctorsFee;
        patients = new ArrayList<>();
    }

    //getters and setters
    public double getDoctorsFee() {
        return doctorsFee;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    //to check if doctor has maximum amount of patients
    public boolean areMaxPatientsAllocated(){
        if(patients.size()>=maxPatientsAllocated) {
            System.out.println("Cant fix an appointment max number of patients exceeded...");
            return true;
        }
        return false;
    }


    //checking visiting patients makes the main thread sleep for 2 seconds to imitate the doctor checking patient
    public void checkPatient(int noOfPatientsToCheck, Scanner stringScanner){
        while(noOfPatientsToCheck>0){

            if(patients.size()<1){
                System.out.println("No patients to check..");
                return;
            }
            System.out.println("Checking patient...");

            try{
                Thread.sleep(2000);
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
            }
            System.out.println("Enter the disease that patient had : ");
            String disease = stringScanner.nextLine();

            System.out.println("Checkup complete...");
            Patient patient = patients.remove(patients.size()-1);
            System.out.println(patient);

            patient.makePayment(doctorsFee,new MedicalRecord(LocalDate.now(),disease,"checkup",0,0,this.doctorId,this.name,this.doctorsFee));

            noOfPatientsToCheck--;
        }
    }

    //visiting admitted patients
    public void visitPatient(){
        if(room==null){
            System.out.println("No patients to visit...");
            return;
        }
        if(room.isOccupied()) {
            Patient patient = room.getPatient();
            System.out.println("visiting patient");
            System.out.println(patient);
            return;
        }

        System.out.println("No Patients to visit...");
    }

    //find if patient by the given id is present under the patients with appointments
    public Patient getPatientById(int id){
        return patients.stream().filter(patient -> patient.getReferenceId()==id).findAny().orElse(null);
    }

}
