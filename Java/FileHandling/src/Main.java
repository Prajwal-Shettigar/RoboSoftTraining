import java.io.*;
import java.util.Scanner;

public class Main {

    public static Scanner stringScanner;
    public static Scanner intScanner;

    public static void main(String[] args) throws IOException,InterruptedException {

        stringScanner = new Scanner(System.in);
        intScanner = new Scanner(System.in);

        userPrompt();



    }


    public static void userPrompt() throws IOException{
        while(true){
            System.out.println("Enter \n" +
                    "1 to Create a File \n" +
                    "2 to Delete a File \n" +
                    "3 to Read from a File \n" +
                    "4 to OverWrite a File \n" +
                    "5 to Append to a File \n" +
                    "6 to Show Directory Contents \n" +
                    "7 to Print File Properties \n" +
                    "8 to Exit");

            int choice = intScanner.nextInt();

            switch(choice){
                case 1:
                    createAFile(enterFileName());
                    break;
                case 2:
                    deleteAFile(enterFileName());
                    break;
                case 3:
                    readFileData(enterFileName());
                    break;
                case 4:
                    overWriteAFile(enterFileName());
                    break;
                case 5:
                    appendToAFile(enterFileName());
                    break;
                case 6:
                    showDirectoryFiles(enterFileName());
                    break;
                case 7:
                    printFileProperties(enterFileName());
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Enter a valid choice");
            }
        }
    }






    //creating a file
    public static void createAFile(String name) throws IOException{
        File file = new File(name);
        if(file.exists()){
            System.out.println("File by the name "+name+" is already present");
            return;
        }

        file.createNewFile();
        System.out.println("File "+name+" is successfully created..");

    }


    //delete a file
    public static void deleteAFile(String name){
        File file = new File(name);
        if(file.exists()){
            file.delete();
            System.out.println("File by the name "+name+" deleted successfully");
            return;
        }

        System.out.println("File "+name+" does not exists..");
    }


    //overwrite file contents
    public static void overWriteAFile(String name) throws IOException{
        File file = new File(name);
        if(!file.exists()){
            System.out.println("File to be overwritten does not exists");

            System.out.println("Do you want to create the file enter yes|no");
            if(!stringScanner.nextLine().equalsIgnoreCase("yes")){
               return;
            }
            createAFile(name);

        }

        System.out.println("Enter the data to be written onto the file : ");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(stringScanner.nextLine());
        fileWriter.close();
    }


    //read from a file
    public static void readFileData(String name) throws FileNotFoundException{
        File file = new File(name);

        if(!file.exists()){
            System.out.println("The file by name "+name+" does not exists ");
            return;
        }

        Scanner scanner = new Scanner(file);
        System.out.println("Contets of file : ");
        while(scanner.hasNext()){
            System.out.println(scanner.nextLine());
        }
    }

    //append to a file
    public static void appendToAFile(String name) throws IOException{
        File file = new File(name);

        if(!file.exists()){
            System.out.println("File does not exists..");
            return;
        }

        PrintWriter printWriter = new PrintWriter(new FileWriter(file,true));
        System.out.println("Enter the contents to written into the file");
        printWriter.append(stringScanner.nextLine());
        printWriter.close();


    }

    //show directory contents
    public static  void showDirectoryFiles(String name){
        File file = new File(name);

        if(!file.exists()){
            System.out.println("File does not exists");
            return;
        }

        if(!file.isDirectory()){
            System.out.println(name+" is not a directory");
            return;
        }

        System.out.println("Files under "+name+" :");
        for(String filename:file.list()){
            System.out.println(filename);
        }

    }


    //print file properties
    public static void printFileProperties(String name){
        File file = new File(name);

        if(!file.exists()){
            System.out.println("Requested file doesnt exists..");
            return;
        }


        System.out.println("File Name => "+file.getName());
        System.out.println("File Parent Directory => "+file.getParent());
        System.out.println("Absolute Path name => "+file.getAbsolutePath());
        System.out.println("Total Space => "+file.getTotalSpace());
        System.out.println("Last Modified => "+file.lastModified());

    }


    //take filename as user input
    public static String enterFileName(){
        System.out.println("Enter the file name : ");
        return stringScanner.nextLine();
    }






}
