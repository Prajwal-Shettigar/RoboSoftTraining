import java.util.Scanner;

public class Main {

    public static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        MyCurrencyFactory myCurrencyFactory = new MyCurrencyFactory();

        System.out.println("Enter the country name : ");
        String countryName = scanner.nextLine();
        MyCurrency myCurrency = myCurrencyFactory.getCurrency(countryName);

        if (myCurrency == null) {
            System.out.println("Country " + countryName + " doesnt exists in our database..");
            return;
        }

        myCurrency.getCurrency();
        myCurrency.getSymbol();


    }
}

