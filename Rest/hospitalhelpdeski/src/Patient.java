public class Patient {


    private int patientId;

    private String patientName;

    private int age;

    private String gender;

    private long telephoneNumber;



    //for in patient
    public Patient(int patientId, String patientName, int age,String gender, long telephoneNumber) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.telephoneNumber = telephoneNumber;
    }

    //for out patient


    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public int getAge() {
        return age;
    }

    public long getTelephoneNumber() {
        return telephoneNumber;
    }
}
