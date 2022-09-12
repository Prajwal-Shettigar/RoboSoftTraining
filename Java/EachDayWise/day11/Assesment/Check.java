public class Check extends Payment{

    private String name;
    private String bankId;

    public Check(float amount, String name, String bankId) {
        super(amount);
        this.name = name;
        this.bankId = bankId;
    }

    public void authorize(){
        System.out.println("Payment of "+super.getAmount()+"has been made through check..");
    }
}
