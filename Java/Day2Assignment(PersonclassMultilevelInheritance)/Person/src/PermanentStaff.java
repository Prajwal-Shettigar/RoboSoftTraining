public class PermanentStaff extends TemporaryStaff{

    public PermanentStaff(String name, int age, String address, int id, long phoneNumber, long salary) {
        super(name, age, address, id, phoneNumber, salary);
    }


    public String toString(){
        return "This is a Permanent staff  with name = "+name+
                "\n age = "+age+"\n address = "+address+"\n id = "+id+
                "\nphone number = "+phoneNumber+"\n salary = "+salary;
    }
}
