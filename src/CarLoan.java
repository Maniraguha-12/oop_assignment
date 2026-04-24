public class CarLoan extends LoanManager {
    private String vehicleType;
    private String vehicleModel;
    private double vehicleValue;
    private int vehicleYear;

    public CarLoan() {
        super();
        this.vehicleType = "";
        this.vehicleModel = "";
        this.vehicleValue = 0.0;
        this.vehicleYear = 0;
    }

    public CarLoan(String loanId, double loanAmount, double interestRate,
                 int loanDuration, String officerName, String branchLocation,
                 String vehicleType, String vehicleModel, double vehicleValue,
                 int vehicleYear) {
        super(loanId, "Car", loanAmount, interestRate, loanDuration, officerName, branchLocation);
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
        this.vehicleValue = vehicleValue;
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public double getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(double vehicleValue) {
        this.vehicleValue = vehicleValue;
    }

    public int getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(int vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    @Override
    public double calculateInterest() {
        double rate = getInterestRate() / 100;
        int months = getLoanDuration();
        double principal = getLoanAmount();
        double interest = principal * rate * (months / 12.0);
        int currentYear = java.time.LocalDate.now().getYear();
        if (currentYear - vehicleYear > 5) {
            interest *= 1.15;
        }
        return interest;
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        if (!super.checkEligibility(customer)) {
            return false;
        }
        double ltv = getLoanAmount() / vehicleValue;
        return ltv <= 0.90;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "  Vehicle Type: " + vehicleType + "\n" +
               "  Vehicle Model: " + vehicleModel + "\n" +
               "  Vehicle Value: " + String.format("%.2f", vehicleValue) + "\n" +
               "  Vehicle Year: " + vehicleYear;
    }
}