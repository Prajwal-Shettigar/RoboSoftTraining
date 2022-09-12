import java.util.Random;
import java.util.Scanner;


public class GuessMyNumber {

    public static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) {


        boolean quit=false;
        int number;
        Random random=new Random();


        while(!quit){
            number=random.nextInt(1,10);

            System.out.println("guess a number between 1 and 10");

            guess(number);

            System.out.println("Press E to exit or Y to play again");

            scanner.nextLine();
            String choice=scanner.nextLine();

            if(choice.equalsIgnoreCase("e")){
                quit=true;
            }


        }
    }

    public static void guess(int number){
        int guess;
        int counter=3;

        while(true && counter>0){
            guess=scanner.nextInt();
            counter--;
            if(number>guess){
                System.out.println("Your guess is lower than the number.. you have "+counter+" chances left ");
            } else if (number==guess) {
                System.out.println("Correct!!!!");
                return;
            }else{
                System.out.println("Your guess is higher than the number..you have "+counter+" chances left ");
            }

        }

        System.out.println("The number was = "+number);
    }
}
