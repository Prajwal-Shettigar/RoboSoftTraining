import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static Scanner intScanner;
    public static Scanner stringScanner;

    public static void main(String[] args) {

        intScanner = new Scanner(System.in);
        stringScanner = new Scanner(System.in);










        //userPrompt();

    }

    public static void userPrompt(){

    }


    //gets the current date
    public static void getTodaysDate(LocalDate localDate){
        System.out.println("Todays Date : "+localDate.format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
    }


    //gets the day month year of given date
    public static void getDayMonthYear(LocalDate localDate){
        System.out.println(" Day : "+localDate.getDayOfWeek()+"  Month : "+localDate.getMonth()+"  Year : "+localDate.getYear());
    }

    //get Particular date info
    public static  void getParticularDate(){
        LocalDate localDate1 = takeStringDate();
        getDayMonthYear(localDate1);
    }


    //check if 2 dates are equal
    public static void equalityOfDates(LocalDate date1,LocalDate date2){
        if(date1.isEqual(date2)){
            System.out.println("Given dates are equal..");
        }else{
            System.out.println("Given dates are not equal");
        }
    }


    //take date as string inputs
    public static  LocalDate takeStringDate(){
        while(true){
            System.out.println(" Date (YYYY-MM-DD) : ");
            String date = stringScanner.nextLine();
            try{
                return LocalDate.parse(date);
            }catch(DateTimeParseException dateTimeParseException){
                System.out.println("Wrong Date format..");
                continue;
            }

        }

    }

    //check if leap year
    public static void checkLeapYear(){
        LocalDate localDate = takeStringDate();
        if(localDate.isLeapYear()){
            System.out.println(localDate.getYear()+" is a Leap year..");
        }else{
            System.out.println(localDate.getYear()+" is not a Leap year..");
        }
    }

    //get the current time in 12 hour format
    public static void getCurrentTime(LocalTime localTime){
        System.out.println("Current Time : "+localTime.format(DateTimeFormatter.ofPattern("hh:mm:SS a")));
    }


    //add hours to current time
    public static void addHourstoTime(LocalTime localTime){
        System.out.println("Enter the number of hours to be added : ");
        long hours = intScanner.nextLong();

        localTime = localTime.plusHours(hours);

        System.out.println("Time after adding "+hours+" hours is : "+localTime.format(DateTimeFormatter.ofPattern("hh:mm:SS a")));

    }

    //get date before and after one year
    public static void findBeforeAndAfterOneYear(LocalDate localDate){
        System.out.println("Date : "+localDate.format(DateTimeFormatter.ofPattern("dd:MM:YYYY")));

        System.out.println("Date after one year : "+localDate.plusYears(1).format(DateTimeFormatter.ofPattern("dd:MM:YYYY")));

        System.out.println("Date before one year : "+localDate.minusYears(1).format(DateTimeFormatter.ofPattern("dd:MM:YYYY")));

    }

    //check for birthday
    public static  void 

}


