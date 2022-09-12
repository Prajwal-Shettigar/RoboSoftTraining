import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner intScanner;

    public static Scanner stringScanner;

    public static ArrayList<State> country;

    public static State currentState;


    public static void main(String[] args) {


        intScanner = new Scanner(System.in);
        stringScanner = new Scanner(System.in);

        country = new ArrayList<>();


        userPrompt();



    }


    //interface for the user
    public static void userPrompt(){
       while (true){
           System.out.println(" Enter \n 1 to add a city \n 2 to display all cities \n 3 to add a state \n 4 to display all states \n 5 to exit ");
           switch(intScanner.nextInt()){
               case 1:

                   addCities();
                   break;
               case 2:
                   displayCities();
                   break;
               case 3:
                   addState();
                   break;
               case 4:
                   displayStates();
                   break;
               case 5:
                   return;
               default:
                   System.out.println("Enter a valid choice");


           }
       }
    }



    //search for a state based on name
    public static State searchState(String state){
        for(State s:country){
            if(s.getStateName().equalsIgnoreCase(state)){
                return s;
            }
        }
        return null;
    }



    //add cities to state
    public static void addCities(){
        System.out.println("Enter the state name : ");
        currentState = searchState(stringScanner.nextLine());
        if(currentState==null){
            System.out.println("Enter a valid state ");
            return;
        }

        System.out.println("Enter the number of cities you want ot add : ");
        int numberOfCities = intScanner.nextInt();

        while(numberOfCities>0){
            System.out.println("Enter the city name ");
            currentState.getCities().add(new City(stringScanner.nextLine()));
            numberOfCities--;
        }
    }


    //display all cities in a state
    public static void displayCities(){
        System.out.println("Enter the state name : ");
        currentState = searchState(stringScanner.nextLine());
        if(currentState==null){
            System.out.println("Enter a valid state ");
            return;
        }
        for(City city: currentState.getCities()){
            System.out.println(city.getCityName());
        }
    }



    //add a state to country list
    public static void addState(){

        System.out.println("Enter the state name : ");
        String stateName = stringScanner.nextLine();
        currentState = searchState(stateName);
        if(currentState == null){
            country.add(new State(stateName));
            return;
        }

        System.out.println("State by the name "+stateName+" is already present");

    }


    //display all the states
    public static  void displayStates(){
        if(country.size()==0){
            System.out.println("No states available in database..");
            return;
        }
        for(State state : country){
            System.out.println(state.getStateName());
        }
    }

}
