import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static ArrayList<Employee> employeeData;
    public static Scanner scanner;

    public static void main(String[] args) {
        employeeData = new ArrayList<>();
        scanner = new Scanner(System.in);

        System.out.println("If you want to manually add records to database press Y/y else press anything else");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("Y")){
            //for manual record insertion
            insertionPrompt();

        }else{
            //for auto insertion of records
            arrayListInitialization();
        }

        //menu for the user
        userPrompt();


    }


    //Main user prompt
    public static void userPrompt(){
        while(true){
            System.out.println("1.Display all records\n2.Delete a record\n3.Insert  records\n4.Search for an Employee \n5.Update Employee Record \n6.Exit");
            int choice = scanner.nextInt();

            switch(choice){
                case 1:
                    displayAllEmployeeData();
                    break;
                case 2:
                    deletionPrompt();
                    break;
                case 3:
                    insertionPrompt();
                    break;
                case 4:
                    searchPrompt();
                    break;
                case 5:
                    updatePrompt();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Enter a valid choice");
            }
        }
    }

    //user prompt for deletion
    public static void deletionPrompt(){
        System.out.println("Enter the id of employee to be deleted");
        int id = scanner.nextInt();
        deleteEmployee(id);
    }

    //user prompt for insertion
    public static  void insertionPrompt(){
        System.out.println("Enter the number of employee records you want to insert");
        int numberofRecords = scanner.nextInt();
        String name;
        int id;
        double salary;
        for(int counter=0;counter<numberofRecords;counter++){

            System.out.println("Enter the employee id : ");
            id = scanner.nextInt();
            Employee e=searchForSimilarEmployee(id);
            if(e!=null){
                System.out.println("Employee with id= "+id +" is already present...try different id" );
                counter--;
                continue;
            }
            scanner.nextLine();
            System.out.println("Enter the employee name : ");
            name = scanner.nextLine();
            System.out.println("Enter the employee salary : ");
            salary = scanner.nextDouble();

            insertEmployee(new Employee(name,id,salary));
        }
    }

    //user interaction prompt for searching an employee record
    public static void searchPrompt(){
        System.out.println("Enter the ID of the employee to be searched : ");
        int id = scanner.nextInt();
        Employee employee=searchForSimilarEmployee(id);
        if(employee==null){
            System.out.println("Employee with id = "+id+" not available..");
            return;
        }
        System.out.println("ID : "+employee.getEmployeeId());
        System.out.println("Name : "+employee.getEmployeeName());
        System.out.println("Salary : "+employee.getEmployeeSalary());
        System.out.println("=======================================");

    }

    //user interaction prompt for employee record updation
    public static void updatePrompt(){
        System.out.println("Enter the ID of the employee to be updated : ");
        int id=scanner.nextInt();
        Employee employee = searchForSimilarEmployee(id);
        if(employee==null){
            System.out.println("Employee record with id = "+id+" is not present in our database...");
            return;
        }
        scanner.nextLine();
        System.out.println("Enter the updated name: ");
        String name= scanner.nextLine();
        System.out.println("Enter the updated salary");
        double salary= scanner.nextDouble();
        employee.setEmployeeName(name);
        employee.setEmployeeSalary(salary);

        System.out.println("Records updated successfully...");
    }



    //intialize the arraylist with values
    public static void arrayListInitialization(){
        Random random = new Random();

        String[] employeeNames = {"abc","def","ghi","jkl","mno"};
        int employeeId = random.nextInt(1000);

        for(int counter =0;counter<5;counter++){
            employeeData.add(new Employee(employeeNames[counter],employeeId++,(100000*random.nextDouble())));
        }
    }

    //inserting employee records
    public static void insertEmployee(Employee employee){
        if(employeeData.add(employee)){
            System.out.println("Employee records inserted successfully..");
            return;
        }

        System.out.println("Insertion error....");
    }

    //deleting employee records
    public static void deleteEmployee(int id){
        if(removalMethod(id)){
            System.out.println("Employee data with id = "+id+" removed successfully");
            return;
        }
        System.out.println("Deletion unsuccessful employee with id = "+id+" is not present in the database...");
    }

    //removing from arraylist
    public static boolean removalMethod(int id){
       return employeeData.removeIf((empid)->{
            if(empid.getEmployeeId()==id){
                return true;
            }
            return false;
        });
    }

    //display all employee details
    public static void displayAllEmployeeData(){
        System.out.println("Printing all employee details..");
        employeeData.forEach((employee)->{
            System.out.println("======================================");
            System.out.println(" ID : "+employee.getEmployeeId());
            System.out.println(" Name : "+employee.getEmployeeName());
            System.out.println(" Salary : "+employee.getEmployeeSalary());
            System.out.println("======================================");
        });
    }





    //search for employee using id in the arraylist
    public static Employee searchForSimilarEmployee(int id){
        for(Employee employee: employeeData){
            if(employee.getEmployeeId()==id) {
                return employee;
            }
        }
        return null;
    }


}
