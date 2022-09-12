import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {


    public static Scanner intScanner;
    public static Scanner stringScanner;

    public static LinkedList<Student> classroom;
    public static void main(String[] args) {
         classroom = new LinkedList<>();

        intScanner = new Scanner(System.in);
        stringScanner = new Scanner(System.in);


        System.out.println("Enter the number of students in the class room :");
        int numberOfStudents = intScanner.nextInt();
        while(numberOfStudents>0){

            try{
                userPrompt();
            }catch(AgeNotWithinRangeException ageNotWithinRangeException){
                System.out.println(ageNotWithinRangeException.getMessage());
                continue;
            }

            numberOfStudents--;
        }

        printAllStudentDetails();


    }

    public static void userPrompt() throws AgeNotWithinRangeException{

        System.out.println("Enter the name of student : ");
        String name = stringScanner.nextLine();

        System.out.println("Enter the age of the student : ");
        int age = ageCheck(intScanner.nextInt());

        System.out.println("Enter the roll number of student : ");
        int rollNumber = intScanner.nextInt();

        System.out.println("Enter the course attended by the student : ");
        String course = stringScanner.nextLine();

        classroom.add(new Student(name,age,rollNumber,course));

    }


    public static int ageCheck(int age) throws AgeNotWithinRangeException{
        if(age<15 || age>21){
            throw new AgeNotWithinRangeException("Age must be in between 15 & 21 ");
        }
        return age;
    }


    public static void printAllStudentDetails(){
        ListIterator<Student> listIterator = classroom.listIterator();

        while(listIterator.hasNext()){
            System.out.println(listIterator.next());
            System.out.println("================================");
        }
    }
}
