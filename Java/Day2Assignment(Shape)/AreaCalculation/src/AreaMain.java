import java.util.Scanner;
public class AreaMain {

    public static void main(String[] args){

        Scanner scanner=new Scanner(System.in);

        System.out.println("Enter the length of rectangle");
        double length=scanner.nextDouble();

        System.out.println("Enter the width of rectangle");
        double width=scanner.nextDouble();

        Rectangle rectangle=new Rectangle(length,width);

        rectangle.area();

        System.out.println("\n================================>>>>\n");

        System.out.println("Enter the length of base of triangle");
        double base=scanner.nextDouble();

        System.out.println("Enter the height of triangle");
        double height=scanner.nextDouble();

        Triangle triangle=new Triangle(base,height);

        triangle.area();

    }
}
