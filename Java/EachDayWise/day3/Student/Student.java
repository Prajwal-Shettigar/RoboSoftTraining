public class Student {

    public String name;
    public int rollNumber;

    public Student(String name, int rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
    }

    public void show(){
        System.out.println("\t\tStudent Details\n" +
                "\tName : "+name+"\n" +
                "\tRoll Number : "+rollNumber);
    }
}
