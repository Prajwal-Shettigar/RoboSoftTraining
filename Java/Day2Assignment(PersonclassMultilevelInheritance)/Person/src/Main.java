public class Main {

    public static void main(String[] args) {
         String name="abc";
         int age=12;
         String address="udupi";
         int id=10;
         long phoneNumber=123456789;
         long salary=1234567;



        //base class object
        Person person = new Person(name,age,address);
        System.out.println(person);

        System.out.println("===========================================");

        //child class object but base of Permanent staff
        TemporaryStaff temporaryStaff=new TemporaryStaff(name,age,address,id,phoneNumber,salary);
        System.out.println(temporaryStaff);

        System.out.println("===========================================");

        //Permanent staff class object
        PermanentStaff permanentStaff=new PermanentStaff(name,age,address,id,phoneNumber,salary);
        System.out.println(permanentStaff);
        System.out.println("===========================================");

        //creating an parent object with child reference
        //you can access methods common to both or that are present in parent class
        //cannot access methods or data members present only in child class
        Person person1=new TemporaryStaff(name,age,address,id,phoneNumber,salary);
        System.out.println(person1);
        person1.printSomething();
        System.out.println("===========================================");
    }




}
