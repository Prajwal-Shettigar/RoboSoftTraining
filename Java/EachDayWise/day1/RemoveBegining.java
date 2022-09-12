import java.util.Scanner;
public class RemoveBegining{

    public static void main(String[] args)
    {
        Scanner scannerObj=new Scanner(System.in);

        System.out.println("Enter input String ");
        String inputString=scannerObj.nextLine();

        System.out.println("Initial String = "+inputString);

        if(inputString.length()>1)
        {
            inputString = removeSubstring(inputString);
        }

        System.out.println("final String = "+inputString);


    }


    public static String removeSubstring(String input)
    {
        String str1=input.substring(input.length()-2,input.length());
        String str2=input.substring(0,2);
        if(str1.equalsIgnoreCase(str2))
        {
            return input.substring(2,input.length());
        }
        return input;
    }
}
