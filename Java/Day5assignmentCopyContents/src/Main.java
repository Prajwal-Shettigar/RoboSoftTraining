import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static Scanner scanner;
    public static void main(String[] args) throws IOException{

        scanner = new Scanner(System.in);


        //creating the 3 files required
        File file1 = new File("File1.txt");

        File file2 = new File("File2.txt");

        File file3 = new File("File3.txt");


        //taking data from user and writing it into file1
        System.out.println("Enter the data to be written into file1");
        writeToFile(file1,scanner.nextLine(),false);

        //taking data from the user and writing it onto file2
        System.out.println("Enter the data to be written into file2");
        writeToFile(file2,scanner.nextLine(),false);

        //writing data from file1 and file2 onto file3
        System.out.println("Writing contents of file1 & file2 to file3");
        writeToFile(file3,readData(file1),false);
        writeToFile(file3,readData(file2),true);

        //printing contents of file1 and file2 onto the user screen
        System.out.println("Content in file3:");
        System.out.println(readData(file3));



    }



    //writing contents onto a file
    public static void writeToFile(File file,String data,boolean append) throws IOException {
        FileWriter writer = new FileWriter(file,append);

        writer.write(data);

        writer.close();

    }

    //reading data from a file
    public static String readData(File file) throws IOException{
        Scanner scanner1 = new Scanner(file);
        String output = "";
        while(scanner1.hasNext()){
            output=output.concat(scanner1.nextLine());
        }
        return output;

    }

}
