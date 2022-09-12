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


        userPrompt();

    }

    public static void userPrompt(){

        while(true){
            System.out.println("Enter \n" +
                    "1. to get todays date \n" +
                    "2. to get current day month year \n" +
                    "3. to get particular date details \n" +
                    "4. to check if 2 dates are equal\n" +
                    "5. to check for recurring events \n" +
                    "6. to check if a year is a leap year \n" +
                    "7. to get current time \n" +
                    "8. to add hours to time \n" +
                    "9. to get date before and after one year \n" +
                    "10 to exit");

            switch(intScanner.nextInt()){
                case 1:
                    getTodaysDate(LocalDate.now());
                    break;

                case 2:
                    getDayMonthYear(LocalDate.now());
                    break;

                case 3:
                    getParticularDate();
                    break;

                case 4:
                    equalityOfDates(takeStringDate(),takeStringDate());
                    break;

                case 5:
                    getRecurringEventDetails(takeStringDate());
                    break;

                case 6:
                    checkLeapYear();
                    break;
                case 7:
                    getCurrentTime(LocalTime.now());
                    break;
                case 8:
                    addHourstoTime(LocalTime.now());
                    break;
                case 9:
                    findBeforeAndAfterOneYear(takeStringDate());
                    break;
                case 10:
                    return;
                default:
                    System.out.println("enter a valid choice..");

            }
        }

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
        System.out.println("enter the year : ");
        int year = intScanner.nextInt();
        LocalDate localDate = LocalDate.of(year,01,01);
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
    public static  void getRecurringEventDetails(LocalDate localDate){
        System.out.println("enter the number of years after which you want to get recurring event details : ");
        long numberOfYears = intScanner.nextLong();

        LocalDate eventDate = localDate.plusYears(numberOfYears);

        System.out.println("Month : "+eventDate.getMonth());
        System.out.println("Year : "+eventDate.getYear());
        System.out.println("Day Of Week : "+eventDate.getDayOfWeek());
        System.out.println("Day of month : "+eventDate.getDayOfMonth());
        System.out.println("Day of year : "+eventDate.getDayOfYear());
    }

}


