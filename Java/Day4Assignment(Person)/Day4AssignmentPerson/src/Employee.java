import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee extends Person {
    long ecNo;
    Date dateOfJoining;
    String designation;

    public Employee(String name,int age,long ecNo,Date dateOfJoining){
        super(name,age);
        this.ecNo = ecNo;
        this.dateOfJoining = dateOfJoining;

    }


    public void display(){
        System.out.println("Name : "+super.name);
        System.out.println("Age : "+super.age);
        System.out.println("EC No : "+ecNo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println("Date of Joining : "+dateFormat.format(dateOfJoining));
    }
}
