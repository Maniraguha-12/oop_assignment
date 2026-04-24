public class HomeLoan extends LoanManager {
    private String propertyType;
    private double propertyValue;
    private String propertyAddress;
    private boolean isNewConstruction;

    public HomeLoan() {
        super();
        this.propertyType = "";
        this.propertyValue = 0.0;
        this.propertyAddress = "";
        this.isNewConstruction = false;
    }

    public HomeLoan(String loanId, double loanAmount, double interestRate,
                   int loanDuration, String officerName, String branchLocation,
                   String propertyType, double propertyValue, String propertyAddress,
                   boolean isNewConstruction) {
        super(loanId, "Home", loanAmount, interestRate, loanDuration, officerName, branchLocation);
        this.propertyType = propertyType;
        this.propertyValue = propertyValue;
        this.propertyAddress = propertyAddress;
        this.isNewConstruction = isNewConstruction;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(double propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public boolean isNewConstruction() {
        return isNewConstruction;
    }

    public void setNewConstruction(boolean newConstruction) {
        isNewConstruction = newConstruction;
    }

    @Override
    public double calculateInterest() {
        double rate = getInterestRate() / 100;
        int months = getLoanDuration();
        double principal = getLoanAmount();
        double interest = principal * rate * (months / 12.0);
        if (isNewConstruction) {
            interest *= 0.95;
        }
        return interest;
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        if (!super.checkEligibility(customer)) {
            return false;
        }
        double ltv = getLoanAmount() / propertyValue;
        return ltv <= 0.85;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "  Property Type: " + propertyType + "\n" +
               "  Property Value: " + String.format("%.2f", propertyValue) + "\n" +
               "  Property Address: " + propertyAddress + "\n" +
               "  New Construction: " + isNewConstruction;
    }
}