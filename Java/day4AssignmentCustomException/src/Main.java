import java.util.Scanner;

public class Main {


    public static Scanner scanner;

    public static void main(String[] args){

        try{
            sumCalculator();
        }catch (HigherThan100Exception higherThan100Exception){
            System.out.println(higherThan100Exception);
        }

    }


    public static void sumCalculator() throws HigherThan100Exception {
        scanner = new Scanner(System.in);

        System.out.println("Enter the value of a : ");
        int a = scanner.nextInt();

        System.out.println("Enter the value of b : ");
        int b = scanner.nextInt();

        int sum=a+b;

        if (sum >= 100) {
            throw new HigherThan100Exception("The sum is greater then 100 ");
        } else {
            System.out.println("Sum of " + a + " & " + b + " = " + sum);
        }

    }




}
