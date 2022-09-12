public class SBI extends Interest {

    public SBI(double rateOfInterest) {
        super.bankName = "State Bank of India";
        super.rateOfInterest = rateOfInterest;
    }

    @Override
    public void getBankName() {
        System.out.println(super.bankName);
    }

    @Override
    public void getRateOfInterest() {
        System.out.println("Rate of interest in SBI = "+super.rateOfInterest);
    }
}
