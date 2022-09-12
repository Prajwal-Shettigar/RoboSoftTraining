public class Rectangle extends Shape{

    Rectangle(double length,double breadth){
        super.base=length;
        super.height=breadth;
    }
    @Override
    public void area() {
        System.out.println("Area of rectangle = "+(super.base*super.height));
    }
}
