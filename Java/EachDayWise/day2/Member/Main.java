import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Manager
        System.out.println("Enter the name of the manager:");
        String mName = scanner.nextLine();

        System.out.println("Enter the age of the manager:");
        int mAge = scanner.nextInt();

        System.out.println("Enter the phone number of the manager:");
        long mPhoneNumber = scanner.nextLong();

        //consumes the enter pressed after phonenumber entry
        scanner.nextLine();


        System.out.println("Enter the address of the manager:");
        String mAddress = scanner.nextLine();

        System.out.println("Enter the salary of the manager:");
        double mSalary = scanner.nextDouble();

        //consumes the enter pressed after salary entry
        scanner.nextLine();

        System.out.println("Enter the department of the manager:");
        String mDepartment = scanner.nextLine();

        Manager manager = new Manager(mName,mAddress,mAge,mPhoneNumber,mSalary,mDepartment);

        manager.printSalary();
        System.out.println(manager);

        System.out.println("===============================================================");

        //Employee
        System.out.println("Enter the name of the employee:");
        String eName = scanner.nextLine();

        System.out.println("Enter the age of the employee:");
        int eAge = scanner.nextInt();

        System.out.println("Enter the phone number of the employee:");
        long ePhoneNumber = scanner.nextLong();

        //consumes the enter pressed after phonenumber entry
        scanner.nextLine();

        System.out.println("Enter the address of the employee:");
        String eAddress = scanner.nextLine();

        System.out.println("Enter the salary of the employee:");
        double eSalary = scanner.nextDouble();

        //consumes the enter pressed after salary entry
        scanner.nextLine();

        System.out.println("Enter employee specialization:");
        String eSpecialization = scanner.nextLine();

        Employee employee = new Employee(eName,eAddress,eAge,ePhoneNumber,eSalary,eSpecialization);

        employee.printSalary();
        System.out.println(employee);

    }
}
