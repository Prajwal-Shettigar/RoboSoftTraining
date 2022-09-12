public class Employee {

    private int id ;
    private String name ;
    private String  gender ;
    private int age ;
    private String department ;
    private int  year_of_joining ;
    private double salary ;

    public Employee(int id, String name, String gender, int age, String department, int year_of_joining, double salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.department = department;
        this.year_of_joining = year_of_joining;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear_of_joining() {
        return year_of_joining;
    }

    public void setYear_of_joining(int year_of_joining) {
        this.year_of_joining = year_of_joining;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee id= " + id +
                "\n name= " + name +
                "\n gender= " + gender +
                "\n age= " + age +
                "\n department= " + department +
                "\n year_of_joining= " + year_of_joining +
                "\n salary= " + salary +"\n";
    }
}
