import java.util.Scanner;

public class Main {


    public static Scanner scanner;
    public static void main(String[] args) {
        checkMarks();

    }

    public static void checkMarks(){
        scanner = new Scanner(System.in);
        int internalMarks,externalMarks;

        try{
            System.out.println("Enter the internal marks");
             internalMarks= checkInternalMarks(scanner.nextInt());

            System.out.println("Enter the external marks");
            externalMarks =checkExternalMarks(scanner.nextInt());
        }catch(HighInternalMarkException highInternalMarkException){
            System.out.println(highInternalMarkException.getMessage());
            return;

        }catch(HighExternalMarkException highExternalMarkException){

            System.out.println(highExternalMarkException.getMessage());
            return;

        }catch(Exception e){

            System.out.println(e.getMessage());
            return;
        }

        System.out.println("total marks = "+(internalMarks+externalMarks));


    }


    public static  int  checkInternalMarks(int internalMarks) throws HighInternalMarkException{
        if(internalMarks>40){
            throw new HighInternalMarkException("Internal marks must be less than 40");
        }
        return internalMarks;
    }

    public static  int checkExternalMarks(int externalMArks) throws HighExternalMarkException{
        if(externalMArks>60){
            throw new HighExternalMarkException("External marks must be less than 60");
        }
        return externalMArks;

    }
}
