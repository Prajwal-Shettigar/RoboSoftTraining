public class Rectangle implements Shape {

    double base;
    double height;

    public Rectangle(double base, double height){
        this.base=base;
        this.height=height;
    }

    @Override
    public double area() {

        return base*height;

    }
}
