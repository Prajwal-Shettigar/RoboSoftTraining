public class Triangle extends  Shape {


    Triangle(double base,double height){
        super.base=base;
        super.height=height;
    }
    @Override
    public void area() {
        System.out.println("Area of triangle = "+(0.5*super.base*super.height));
    }
}

