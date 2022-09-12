@FunctionalInterface
public interface Add {

    public static void staticMethod(){
        System.out.println("This is the static method");
    }

    //private method can be used only inside the class
    private void privateMethod(){
        System.out.println("This is a private method");
    }

    public default void defaultMethod(int a,int b){
        System.out.println("this is the default method");
    }
    public  void add(int a,int b);
//    public void add(double a,double b);

//    public void add(String  s);
    }
