import java.time.LocalDate;
import java.util.Date;

public class MedicalRecord {

    private LocalDate visitingDate;
    private String disease;
    private String typeOfVisit;
    private int noOfDaysAdmitted;
    private int roomId;
    private int doctorId;
    private String nameOfDoctor;
    private double feePaid;


    //constructor
    public MedicalRecord(LocalDate visitingDate, String disease, String typeOfVisit, int noOfDaysAdmitted, int roomId,int doctorId, String nameOfDoctor, double feePaid) {
        this.visitingDate = visitingDate;
        this.disease = disease;
        this.typeOfVisit = typeOfVisit;
        this.noOfDaysAdmitted = noOfDaysAdmitted;
        this.roomId = roomId;
        this.doctorId = doctorId;
        this.nameOfDoctor = nameOfDoctor;
        this.feePaid = feePaid;
    }


    @Override
    public String toString() {
        return "MedicalRecord{" +
                "visitingDate=" + visitingDate +
                ", disease='" + disease + '\'' +
                ", typeOfVisit='" + typeOfVisit + '\'' +
                ", noOfDaysAdmitted=" + noOfDaysAdmitted +
                ", roomId=" + roomId +
                ", doctorId=" + doctorId +
                ", nameOfDoctor='" + nameOfDoctor + '\'' +
                ", feePaid=" + feePaid +
                '}';
    }
}
