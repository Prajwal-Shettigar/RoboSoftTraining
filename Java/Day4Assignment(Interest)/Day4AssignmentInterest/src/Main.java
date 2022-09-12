public class Main {
    public static void main(String[] args) {

        //creating axis object
        System.out.println("creatinng Axis object");
        display(new Axis(2.4));

        //creating HDFC object
        System.out.println("creatinng HDFC object");
        display(new HDFC(3.3));


        //crating SBI object
        System.out.println("creatinng SBI object");
        display(new SBI(2.9));

        

    }


    //display bank details
    public static void display(Interest interest){
        interest.getBankName();
        interest.getRateOfInterest();
    }
}
