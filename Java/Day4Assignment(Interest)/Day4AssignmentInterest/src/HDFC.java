public class HDFC extends Interest{

    public HDFC(double rateOfInterest) {
        super.bankName = "HDFC Bank";
        super.rateOfInterest = rateOfInterest;
    }

    @Override
    public void getBankName() {
        System.out.println(super.bankName);
    }

    @Override
    public void getRateOfInterest() {
        System.out.println("Rate of interest in HDFC = "+super.rateOfInterest);
    }
}
