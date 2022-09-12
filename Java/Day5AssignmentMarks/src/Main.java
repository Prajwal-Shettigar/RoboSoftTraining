import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static Scanner scanner;
    public static void main(String[] args) throws FileNotFoundException{
        scanner = new Scanner(System.in);
        System.out.println("Enter the filename : ");
        try {
            displayTotalandAverage(new File(scanner.nextLine()));
        }catch (FileNotFoundException fileNotFoundException){
            System.out.println("Enter a valid file name");
        }




    }


    public static void displayTotalandAverage(File file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);

        int studentCounter= 0;
        int sum = 0;

        while(fileScanner.hasNext()){
            String studentMarks = fileScanner.nextLine();
            studentCounter++;

            String[] markArray = studentMarks.split(" ");

            sum=0;

            for(String mark : markArray){
                sum+=Integer.parseInt(mark);
            }

            System.out.println("Sum & Average of Student "+studentCounter);
            System.out.println("Sum = "+sum);
            System.out.println("Average = "+(sum/ markArray.length));
            System.out.println();
        }

    }
}
