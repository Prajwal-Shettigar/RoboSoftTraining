import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner;
    public static String fileContent;
    public static void main(String[] args) throws IOException {

        scanner = new Scanner(System.in);

        //creating a file
        File file = new File("FileforWordCount.txt");

        //taking data from the user
        System.out.println("Enter the contents to be written onto the file:");
        writeToFile(file,scanner.nextLine(),true);

        //printing output to the user
        ArrayList<Integer> output = readFromFileandCount(file);
        System.out.println("Contents of file : ");
        System.out.println(fileContent);
        System.out.println("Number of lines in the file = "+output.get(0));
        System.out.println("Number of words in the file = "+output.get(1));
        System.out.println("Number of characters in the file = "+output.get(2));



    }


    //writing the contents ont o a file
    public static void writeToFile(File file,String data,boolean append) throws IOException {
        FileWriter writer = new FileWriter(file,append);

        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.println(data);

        writer.close();
        printWriter.close();

    }


    //calculate the number of lines,words,characters from the data read from the file
    public static ArrayList readFromFileandCount(File file) throws IOException{
        Scanner scanner1 = new Scanner(file);
        ArrayList<Integer> arrayList = new ArrayList<>();

        int lineCounter = 0;
        int wordCounter = 0;
        int characterCounter = 0;
        fileContent = "";
        String line="";
        String[] words;

        while(scanner1.hasNext()){
            line = scanner1.nextLine();
            fileContent = fileContent.concat(line+"\n");
            lineCounter++;

            words = line.split(" ");
            wordCounter+=words.length;

            for(String word:words){
                characterCounter+=word.length();
            }

        }
        arrayList.add(lineCounter);
        arrayList.add(wordCounter);
        arrayList.add(characterCounter);

        return arrayList;





    }
}
