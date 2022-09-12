public class TemporaryStaff extends Person{

    int id;
    long phoneNumber;
    long salary;

    public TemporaryStaff(String name, int age, String address, int id, long phoneNumber, long salary) {
        super(name, age, address);
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
    }


    public String toString(){
        return "This is a Temporary staff  with name = "+name+
                "\n age = "+age+"\n address = "+address+"\n id = "+id+
                "\nphone number = "+phoneNumber+"\n salary = "+salary;
    }



}
