public class Axis extends Interest{
    public Axis(double rateOfInterest) {
        super.bankName = "Axis Bank";
        super.rateOfInterest = rateOfInterest;
    }

    @Override
    public void getBankName() {
        System.out.println(super.bankName);
    }

    @Override
    public void getRateOfInterest() {
        System.out.println("Rate of interest in Axis Bank = "+super.rateOfInterest);
    }
}
