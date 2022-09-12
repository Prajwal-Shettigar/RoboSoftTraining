public class Employee extends Member{
    String specialization;

    public Employee(String name, String address, int age, long phoneNumber, double salary, String specialization) {
        super(name, address, age, phoneNumber, salary);
        this.specialization = specialization;
    }

    @Override
    public void printSalary() {
        System.out.println("Salary of the employee is = "+salary);
    }

    @Override
    public String toString() {
        return "\t\tEmployee Details\n" +
                "\tName = "+name+"\n" +
                "\tAddress = "+address+"\n" +
                "\tAge = "+age+"\n" +
                "\tPhone Number = "+phoneNumber+"\n" +
                "\tSalary = "+salary+"\n" +
                "\tSpecialization = "+specialization;
    }
}
