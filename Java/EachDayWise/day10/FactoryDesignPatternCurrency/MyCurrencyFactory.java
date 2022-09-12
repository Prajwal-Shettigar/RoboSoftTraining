public class MyCurrencyFactory {

    public MyCurrency getCurrency(String countryName){
        if(countryName.equalsIgnoreCase("canada")){
            return new Canada();
        }else if (countryName.equalsIgnoreCase("china")){
            return new China();
        }

        return null;
    }
}
