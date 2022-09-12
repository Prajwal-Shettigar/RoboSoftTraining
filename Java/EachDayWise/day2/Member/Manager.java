public class Manager extends Member{
    String department;

    public Manager(String name, String address, int age, long phoneNumber, double salary, String department) {
        super(name, address, age, phoneNumber, salary);
        this.department = department;
    }


    @Override
    public void printSalary() {
        System.out.println("Manager salary is = "+salary);
    }

    @Override
    public String toString() {
        return "\t\tManager Details\n" +
                "\tName = "+name+"\n" +
                "\tAddress = "+address+"\n" +
                "\tAge = "+age+"\n" +
                "\tPhone Number = "+phoneNumber+"\n" +
                "\tSalary = "+salary+"\n" +
                "\tDepartment= "+department;
    }
}
