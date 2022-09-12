public class Rectangle extends Shape {

        //Constructor
        Rectangle(double length,double width){
            super(length,width);
            System.out.println("A Rectangle is created with length = "+length+" & width = "+width);
        }

        //Overidden
        //Calculate rectangle area
        public void area()
        {
            double area=base*height;
            System.out.println("Area of rectangle is = "+area);
        }
}