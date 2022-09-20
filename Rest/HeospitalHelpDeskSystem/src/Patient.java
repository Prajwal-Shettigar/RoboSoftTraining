import java.util.ArrayList;
import java.util.List;

public class Patient {

    private int referenceId;
    private String name;
    private int age;
    private String gender;
    private long telephoneNumber;
    private int noOfDaysToBeAdmitted;
    private Insurance insuranceDetails;
    private List<MedicalRecord> medicalRecords;

    private Room room;


    //constructor
    public Patient(int referenceId, String name, int age, String gender, long telephoneNumber, Insurance insuranceDetails) {
        this.referenceId = referenceId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.telephoneNumber = telephoneNumber;
        this.insuranceDetails = insuranceDetails;
        medicalRecords = new ArrayList<>();
        System.out.println("Patient registered successfully..");
    }


    //getters and setters
    public int getReferenceId() {
        return referenceId;
    }


    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }


    //patient making payment and also the medical record being added to patient medical history
    public void makePayment(double paymentAmount,MedicalRecord medicalRecord){
        medicalRecords.add(medicalRecord);
        System.out.println("Amount of \'"+paymentAmount+"\' has been successfully paid...");
    }



    @Override
    public String toString() {
        return "Patient{" +
                "referenceId=" + referenceId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", telephoneNumber=" + telephoneNumber +
                ", noOfDaysToBeAdmitted=" + noOfDaysToBeAdmitted +
                ", insuranceDetails=" + insuranceDetails +
                ", medicalRecords=" + medicalRecords +
                '}';
    }
}
