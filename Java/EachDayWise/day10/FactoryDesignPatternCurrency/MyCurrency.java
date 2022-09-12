import java.util.Currency;

public  class MyCurrency {

    Currency currency;


    public void getCurrency() {
        System.out.println(currency.getDisplayName());
    }

    public void getSymbol() {
        System.out.println(currency.getSymbol());
    }
}
