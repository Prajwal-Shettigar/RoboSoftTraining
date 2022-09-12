public class EqualityofStringsIgnoreCase{
    public static void main(String[] args)
    {
        String str1="Data1";
        String str2="Data2";
        String str3="Data1";
        String str4="DATA1";

        checkStringEquality(str1,str2);

        checkStringEquality(str1,str3);

        checkStringEquality(str1,str4);



    }
    public static void checkStringEquality(String st1,String st2){
        System.out.println("For String1 = "+st1+"& String2 = "+st2);
        if(st1.equalsIgnoreCase(st2))
        {
            System.out.println("Given Strings contain same data");
        }
        else
        {
            System.out.println("Given Strings do not contain same data");
        }

    }


}
