import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

//       add2.add("hello");


//        Add add = Referable::add;
//        add.add(2,3);
//
//
//        Add sum = new Referable()::sum;
//        sum.add(3,4);
//
//        Add constructor = Referable::new;
//
//        constructor.add(4,8);
//
//
//        String z= 1+2+"a"+2*3+"b";
//        System.out.println(z);
//
//        Add.staticMethod();
//
//        Add var = new Add() {
//            @Override
//            public void add(int a, int b) {
//
//            }
//        }::defaultMethod;
//        var.add(2,3);


//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter  number1");
//        int num1 = scanner.nextInt();
//        System.out.println("Enter  number2");
//        int num2 = scanner.nextInt();
//        System.out.println("Enter  number3");
//        int num3 = scanner.nextInt();
//        System.out.println("Enter  number4");
//        int num4 = scanner.nextInt();
//
//
//
//        sum(num1,num2);
//        sum(num3,num4);


        List<Integer> list =  new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(i);
        }

        list.stream().map(a->a*a*a).forEach(System.out::println);

        System.out.println(list.stream().allMatch(x->x>10));
        System.out.println(list.stream().anyMatch(x->x==3));

        List<String> vowels = List.of("a", "e", "i", "o", "u");

// sequential stream - nothing to combine
        StringBuilder result = vowels.stream().collect(StringBuilder::new, (x, y) -> x.append(y),
                (a, b) -> a.append("==").append(b));
        System.out.println(result.toString());

// parallel stream - combiner is combining partial results
        StringBuilder result1 = vowels.parallelStream().collect(StringBuilder::new, (x, y) -> x.append(y),
                (a, b) -> a.append("=").append(b));
        System.out.println(result1.toString());

       list.stream().map(Function.identity()).forEach(System.out::println);

        System.out.println(list.stream().collect(Collectors.toCollection(HashSet::new)).toString());

       HashSet<> hashset= (HashSet) list.stream().to;

        System.out.println();



    }


    public static void sum(int a,int b){
        System.out.println("inside sum");
        System.out.println("adding 2 numbers");
        System.out.println("adding......");
        System.out.println(a+b);
    }
}
