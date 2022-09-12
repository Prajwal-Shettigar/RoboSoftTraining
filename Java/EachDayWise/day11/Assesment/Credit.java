import java.util.Date;

public class Credit extends Payment{

    private String  name;
    private static final String type="Installment credit";
    private Date expDate;

    public Credit(float amount, String name) {
        super(amount);
        this.name = name;
        this.expDate =  new Date(2022,8,27);;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void authorize(Customer customer){
        System.out.println(super.getAmount()+" has been added to your credit");
        customer.setCredit(customer.getCredit()+super.getAmount());

    }
}
