public class Person {
    String name;
    int age;
    String address;


    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

   public String toString(){
        return "This is a person with name = "+name+
                "\n age = "+age+"\n address = "+address;
   }


    public void printSomething(){
        System.out.println("This is the temporary staff class object");
    }
}
