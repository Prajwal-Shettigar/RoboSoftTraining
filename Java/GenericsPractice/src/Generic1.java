//generic class

public class Generic1 <T>{

    T value;

    Generic1(T value){
        this.value = value;
    }

    public  T getValue(){
        return value;
    }

    //generic method
    public <E,U> U doesSomething(E value1,U value2){
        System.out.println(value1);
        return value2;
    }

    public <E extends Number> E upperLimit(E value1){
        return value1;
    }


    //super not allowed in methods
//    public <E super Child> E lowerLimit(){
//
//    }


}
