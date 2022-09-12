import java.util.*;

public class ReverseArray{
    public static void main(String[] args)
    {

        int[] array={1,2,3,4,5,6};
        int[] reversedArray=reverse(array);

        System.out.println("Initial Array"+Arrays.toString(array));

        System.out.println("Reversed Array"+Arrays.toString(reversedArray));


    }


    public static int[] reverse(int[] array)
    {

        int[] reversedArray=new int[array.length];
        int position=0;

        for(int counter=array.length-1;counter>-1;counter--)
        {
            reversedArray[position]=array[counter];
            position++;

        }
        return reversedArray;
    }

}