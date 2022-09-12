public class Cash extends Payment{

    private float tendetredAmount;

    public Cash(float amount, float tendetredAmount) {
        super(amount);
        this.tendetredAmount = tendetredAmount;
    }


    //returns change if customer pays more than the final amount to be paid
    public void returnChange(){
        if(tendetredAmount>super.getAmount()){
            System.out.println("Here is your change"+(tendetredAmount-super.getAmount()));
            return;

        }
        System.out.println("Thank you for using our service..");
    }
}
