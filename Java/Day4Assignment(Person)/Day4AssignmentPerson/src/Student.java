public class Student extends Person{
    int rollNumber;
    String branch;

    public Student(String name,int age,int rollNumber, String branch) {
        super(name,age);
        this.rollNumber = rollNumber;
        this.branch = branch;
    }

    @Override
    public void display() {
        System.out.println("Name : "+super.name);
        System.out.println("Age : "+super.age);
        System.out.println("Roll Number : "+rollNumber);
        System.out.println("Branch : "+branch);
    }
}
