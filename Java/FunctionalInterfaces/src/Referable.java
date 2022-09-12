public class Referable {

    Referable(int a,int b){
        System.out.println("Contructor Sum  of "+a+" & "+b+" = "+(a+b));
    }

    Referable(){

    }

    public static void add(int a,int b){
        System.out.println("ADD method Sum  of "+a+" & "+b+" = "+(a+b));
    }

    public void sum(int a,int b){
        System.out.println("SUM method Sum  of "+a+" & "+b+" = "+(a+b));
    }
    public void total(int a,int b){
        System.out.println("TOTAL method Sum  of "+a+" & "+b+" = "+(a+b));
    }

}
