import java.util.Date;
import java.util.Scanner;

public class MainDemoClass {

    static Scanner scanner;

    public static void main(String[] args) {

        autoDisplay();

    }


    public static void createObjectandDisplay(Person personReference){

        personReference.display();
        System.out.println("=====================================");
    }


    public static void autoDisplay(){
        String name = "Prajwal";
        int age = 21;
        long ecNo = 12345;
        Date doj = new Date();
        int rollNo = 29;
        String branch = "CSE";
        String facultyDesignation = "Assistant Professor";
        String staffDesignation = "Technical";

        //student object creation
        createObjectandDisplay(new Student(name,age,rollNo,branch));


        //employee object creation
        createObjectandDisplay(new Employee(name,age,ecNo,doj));


        //staff object creation
        createObjectandDisplay(new Staff(name,age,ecNo,doj,staffDesignation));

        //faculty object creation
        createObjectandDisplay(new Faculty(name,age,ecNo,doj,facultyDesignation));
    }





}
