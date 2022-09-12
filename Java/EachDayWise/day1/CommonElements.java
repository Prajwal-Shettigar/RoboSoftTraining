import java.util.*;
public class CommonElements{

    public static void main(String[] args)
    {

        int[] array1={1,2,3,4,5,6};
        int[] array2={7,8,3,2,9,0};
        int[] array3={7,8,9,0,7,8,9};


        findCommon(array1,array2);

        findCommon(array1,array3);
    }




    public static void findCommon(int[] array1,int[] array2)
    {
        System.out.println("Checking for Arrays");
        System.out.println("Array1 = "+Arrays.toString(array1));
        System.out.println("Array2 = "+Arrays.toString(array2));
        int numberOfCommonElements=0;

        for(int array1Counter=0;array1Counter<array1.length;array1Counter++)
        {
            for(int array2Counter=0;array2Counter<array2.length;array2Counter++)
            {
                if(array1[array1Counter]==array2[array2Counter])
                {
                    System.out.println(array1[array1Counter]);
                    numberOfCommonElements++;
                }
            }
        }

        if(numberOfCommonElements==0)
        {
            System.out.println("No Common Elements");
        }
    }
}

