import java.util.Date;

public class Staff extends Employee {

    public Staff(String name, int age, long ecNo, Date dateOfJoining,String designation) {
        super(name, age, ecNo, dateOfJoining);
        super.designation = designation;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Designation : "+designation);
    }
}
