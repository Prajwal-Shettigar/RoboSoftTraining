import java.util.Currency;
import java.util.Locale;

public class China extends MyCurrency{

    public China() {
        currency = Currency.getInstance(Locale.CHINA);
    }
}
