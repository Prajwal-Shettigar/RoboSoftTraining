import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
//        usingLocalDate();

        usingLocalTime();

    }

    public static void usingLocalTime(){

        LocalTime localTime = LocalTime.now();
        LocalTime localTime1 = LocalTime.of(12,30);
        LocalTime localTime2 = LocalTime.parse("12:35");
        LocalTime localTime3 = LocalTime.ofNanoOfDay(1200000000);
        LocalTime localTime4 = LocalTime.ofSecondOfDay(12000);


        System.out.println(localTime);
        System.out.println(localTime1);
        System.out.println(localTime2);
        System.out.println(localTime3);
        System.out.println(localTime4);

        System.out.println(localTime.plusHours(1));
        System.out.println(localTime.minusHours(1));
        System.out.println(localTime.plusMinutes(20));
        System.out.println(localTime.minusSeconds(20));
        System.out.println(localTime.plusNanos(300000));

    }


    //using LocalDate methods
    public static void usingLocalDate(){

        //localdate object creation
        LocalDate localDate = LocalDate.now();
        LocalDate localDate1 = LocalDate.of(2018,12,31);
        LocalDate localDate2= LocalDate.parse("2001-05-31");


        System.out.println(localDate.format(DateTimeFormatter.ISO_DATE));


        System.out.println(localDate1);

        System.out.println(localDate2);
        System.out.println(localDate);

        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.getDayOfYear());

        System.out.println(localDate.minusDays(2));
        System.out.println(localDate.plusDays(2));
        System.out.println(localDate.minusMonths(2));
        System.out.println(localDate.plusMonths(2));
        System.out.println(localDate.plusYears(1));
        System.out.println(localDate.plusWeeks(2));

//        localDate1.datesUntil(localDate).forEach((a)->{
//            System.out.println(a);
//        });

//        System.out.println("Dates until");
//        localDate1.datesUntil(localDate, Period.of(0,1,0)).forEach((a)->{
//            System.out.println(a);
//        });

        System.out.println(localDate1.getChronology());

        System.out.println(localDate.isLeapYear());
    }



}
