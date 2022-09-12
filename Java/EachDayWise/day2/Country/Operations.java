import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Operations {

    Country[] countries;


    //initializes countries  manually
    public void assignCountryArray(Country[] countries){
        this.countries=countries;
    }

    //print all the countries
    public void printCountries(){
        for(Country country:countries){
            System.out.println("Available counties in database");
            System.out.println("========================>>>>>");
            System.out.println("\tName : "+country.getName());
            System.out.println("\tCapital : "+country.getCapital());
            System.out.println("\tPopulation : "+country.getPopulation());
            System.out.println("========================>>>>>");
        }
    }


    //auto intialize countries
    public void preAssignCounties(){
        countries=new Country[5];
        String[] countryNames={"India","Pakistan","Japan","Afghanistan","South Korea"};
        long[] countryPopulations={1380000000,220000000,120000000,230000000,130000000};
        String[] countryCapitals={"New Delhi","Ismabad","Tokyo","Kabul","Seoul"};

        for(int counter=0;counter<5;counter++){
            countries[counter] = new Country(countryNames[counter],countryCapitals[counter],countryPopulations[counter]);
        }
    }

    //search for a country
    public void searchCountry(String name){
        for(Country country:countries){
            if(name.equalsIgnoreCase(country.name)){
                System.out.println("========================>>>>>");
                System.out.println("\tName : "+country.getName());
                System.out.println("\tCapital : "+country.getCapital());
                System.out.println("\tPopulation : "+country.getPopulation());
                System.out.println("========================>>>>>");
                return;
            }
        }
        System.out.println("========================>>>>>");
        System.out.println("Country by the name = "+name+" is not present in our database...");
        System.out.println("========================>>>>>");

    }

    //search country with highest population
    public  void searchHighestPopulation(){

        if(countries.length<1){
            System.out.println("Empty Database");
            return;
        }
        long maxPopulation=countries[0].getPopulation();
        String name=countries[0].getName();

        for(Country country:countries){
            if(country.getPopulation()>maxPopulation){
                maxPopulation=country.getPopulation();
                name=country.getName();
            }
        }

        System.out.println(name+" has the highest population with population = "+maxPopulation);
    }
}
