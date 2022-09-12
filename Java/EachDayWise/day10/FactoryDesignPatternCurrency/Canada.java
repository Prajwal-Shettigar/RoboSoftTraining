import java.util.Currency;
import java.util.Locale;

public class Canada extends MyCurrency{

    public Canada() {
        currency = Currency.getInstance(Locale.CANADA);
    }
}
