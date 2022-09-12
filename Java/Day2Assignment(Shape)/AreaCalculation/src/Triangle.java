public class Triangle extends Shape {

    //Constructor
    Triangle(double base,double height){
        super(base,height);
        System.out.println("A Triangle is created with base = "+base+" & height = "+height);
    }


    //Overiding area of parent class
    //calculating triangle area
    public void area(){
        double area=0.5*base*height;
        System.out.println("Area of triangle is = "+area);
    }
}