public class Main {

    public static void main(String[] args) {


        Shape rectangle = new Rectangle(3,5);
        Shape triangle = new Triangle(8,4);


        System.out.println("Rectangle area = "+rectangle.area());

        System.out.println("Triangle area = "+triangle.area());

    }
}
