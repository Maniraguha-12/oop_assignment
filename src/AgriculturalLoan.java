public class AgriculturalLoan extends LoanManager {
    private String farmType;
    private double farmSize;
    private String farmLocation;
    private String cropType;
    private boolean hasFarmInsurance;

    public AgriculturalLoan() {
        super();
        this.farmType = "";
        this.farmSize = 0.0;
        this.farmLocation = "";
        this.cropType = "";
        this.hasFarmInsurance = false;
    }

    public AgriculturalLoan(String loanId, double loanAmount, double interestRate,
                       int loanDuration, String officerName, String branchLocation,
                       String farmType, double farmSize, String farmLocation,
                       String cropType, boolean hasFarmInsurance) {
        super(loanId, "Agricultural", loanAmount, interestRate, loanDuration, officerName, branchLocation);
        this.farmType = farmType;
        this.farmSize = farmSize;
        this.farmLocation = farmLocation;
        this.cropType = cropType;
        this.hasFarmInsurance = hasFarmInsurance;
    }

    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    public double getFarmSize() {
        return farmSize;
    }

    public void setFarmSize(double farmSize) {
        this.farmSize = farmSize;
    }

    public String getFarmLocation() {
        return farmLocation;
    }

    public void setFarmLocation(String farmLocation) {
        this.farmLocation = farmLocation;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public boolean isHasFarmInsurance() {
        return hasFarmInsurance;
    }

    public void setHasFarmInsurance(boolean hasFarmInsurance) {
        this.hasFarmInsurance = hasFarmInsurance;
    }

    @Override
    public double calculateInterest() {
        double rate = getInterestRate() / 100;
        int months = getLoanDuration();
        double principal = getLoanAmount();
        double interest = principal * rate * (months / 12.0);
        if (hasFarmInsurance) {
            interest *= 0.9;
        }
        return interest;
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        if (!super.checkEligibility(customer)) {
            return false;
        }
        return farmSize > 0;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "  Farm Type: " + farmType + "\n" +
               "  Farm Size (hectares): " + String.format("%.2f", farmSize) + "\n" +
               "  Farm Location: " + farmLocation + "\n" +
               "  Crop Type: " + cropType + "\n" +
               "  Has Farm Insurance: " + hasFarmInsurance;
    }
}