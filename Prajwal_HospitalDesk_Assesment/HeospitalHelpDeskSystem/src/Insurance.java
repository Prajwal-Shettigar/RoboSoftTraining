import java.time.LocalDate;

public class Insurance {

    private int insuranceId;
    private String name;
    private int age;
    private String gender;
    private LocalDate expiryDate;

    public static final float discount = 0.5f;


    //Constructor
    public Insurance(int insuranceId, String name, int age, String gender,LocalDate expiryDate) {
        this.insuranceId = insuranceId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "insuranceId=" + insuranceId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
