import java.util.Scanner;

public class RemoveSubstring{
    public static void main(String[] args){
        Scanner scannerObj=new Scanner(System.in);
        String inputString=scannerObj.nextLine();

        System.out.println("Initial String = "+inputString);
        String outputString=removeBegining(inputString);
        System.out.println("Final String = "+outputString);
    }

    public static String removeBegining(String inputString)
    {
        String[] inputArray=inputString.split(" ");
        if(inputArray.length>1)
        {
            if(inputArray[0].length()==2 && inputArray[inputArray.length-1].length()==2)
            {
                return inputString.substring(3,inputString.length());
            }
        }
        return inputString;
    }
}
