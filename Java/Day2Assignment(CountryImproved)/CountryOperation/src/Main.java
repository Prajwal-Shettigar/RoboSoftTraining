import java.util.Scanner;

public class Main {
    public static Scanner scanner=new Scanner(System.in);
    public static Operations operations=new Operations();

    public static void main(String[] args) {

   //primary prompt for user
    while(true){
        System.out.println("1.Use Pre Assigned Countries\n" +
                "2.Enter Country Details Manually\n" +
                "3.Exit\n" +
                "Enter your choice");
        int choice=scanner.nextInt();

        switch(choice){
            case 1:
                useAssignedCountries();
                break;

            case 2:
                manualEntry();
                break;

            case 3:
                return;

            default:
                System.out.println("Enter a valid choice");
        }
    }

    }


    //use auto assigned countries
    public static void useAssignedCountries(){
        operations.preAssignCounties();
        operations.printCountries();
        userPrompt();

        }

   //use manually assigned countries
    public static void manualEntry(){
        System.out.println("Enter the number of countries");
        int number = scanner.nextInt();

        Country[] countries=new Country[number];
        scanner.nextLine();
        String name,capital;
        long population;

        for(int counter=0;counter<number;counter++){
            System.out.println("Enter country "+(counter+1)+" name:");
            name=scanner.nextLine();
            System.out.println("Enter country "+(counter+1)+" capital:");
            capital=scanner.nextLine();
            System.out.println("Enter country "+(counter+1)+" population:");
            population= scanner.nextLong();
            scanner.nextLine();

            countries[counter]=new Country(name,capital,population);
        }
        operations.assignCountryArray(countries);
        userPrompt();
    }

    //search for a country
    public static void searchForCountry(){
        scanner.nextLine();
        System.out.println("Enter the name of the country to search for");
        String name=scanner.nextLine();
        operations.searchCountry(name);
    }

    //search for country with highest population
    public static void countryWithHighPopulation(){
        operations.searchHighestPopulation();
    }


    //secondary prompt for user
    public static void userPrompt() {
        while (true) {
            System.out.println("1.Search for a country\n" +
                    "2.Get Country with Highest Population\n" +
                    "3.Back");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    searchForCountry();
                    break;

                case 2:
                    countryWithHighPopulation();
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Enter a valid choice");
            }
        }
    }
}
