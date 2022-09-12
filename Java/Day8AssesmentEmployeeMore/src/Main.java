import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner stringScanner;
    public static Scanner otherScanner;

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();

        stringScanner = new Scanner(System.in);
        otherScanner = new Scanner(System.in);

        userPrompt(employees);
    }




    //user prompt
    public static void userPrompt(ArrayList<Employee> employees){
        while(true) {
            System.out.println("Enter \n" +
                    "1 to Add Employee \n" +
                    "2 to get employees who joined after a particular year \n" +
                    "3 to get employee with age above a particular limit  \n" +
                    "4 to print all departments in the organization \n" +
                    "5 to exit ");
            switch (otherScanner.nextInt()) {
                case 1:
                    System.out.println("Enter the Number of employees to add : ");
                    addEmployee(employees, otherScanner.nextInt());
                    break;

                case 2:
                    System.out.println("Enter the minimum joining year for the employee ");
                    getEmployeesJoinedAfter(employees,otherScanner.nextInt());
                    break;

                case 3:
                    System.out.println("Enter the minimum age for the employee : ");
                    findEmployeeAboveAge(employees,otherScanner.nextInt());
                    break;

                case 4:
                    printAllDeparments(employees);
                    break;

                case 5:
                    return;

                default :
                    System.out.println("Enter a valid choice ");
            }
        }
    }

    //add an employee onto the list
    public static void addEmployee(ArrayList<Employee> employees,int maxCount){
        int count =1;
        while(count<=maxCount){
            System.out.println("Enter employee "+count+" id : ");
            int id = otherScanner.nextInt();

            System.out.println("Enter employee "+count+" name : ");
            String name = stringScanner.nextLine();

            System.out.println("Enter employee "+count+" gender : ");
            String gender = stringScanner.nextLine();

            System.out.println("Enter employee "+count+" age : ");
            int age = otherScanner.nextInt();

            System.out.println("Enter employee "+count+" department : ");
            String department = stringScanner.nextLine();

            System.out.println("Enter employee "+count+" year of joining : ");
            int year_of_joining = otherScanner.nextInt();

            System.out.println("Enter employee "+count+" salary ");
            employees.add(new Employee(id, name, gender,  age, department,year_of_joining,otherScanner.nextDouble()));
            count++;
        }
    }


    //get employee who joined on or after a particular year
    public static void getEmployeesJoinedAfter(ArrayList<Employee> employees,int year){
        System.out.println("List of employees who joined after "+year+" are : ");
        employees.stream().filter(employee -> employee.getYear_of_joining()>=year).forEach(System.out::println);
    }

    //print all departments in the organization
    public static void printAllDeparments(ArrayList<Employee> employees){
        System.out.println("List of available departments in the organization : ");
        employees.stream().map(Employee::getDepartment).distinct().forEach(System.out::println);
    }


    //get employees above a particular age
    public static void findEmployeeAboveAge(ArrayList<Employee> employees,int age){
        System.out.println("List of employees above the age of "+age+" are : ");
        employees.stream().filter(employee -> employee.getAge()>=age).forEach(System.out::println);
    }
}
