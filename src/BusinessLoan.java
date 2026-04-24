public class BusinessLoan extends LoanManager {
    private String businessName;
    private String businessType;
    private double annualRevenue;
    private int yearsInBusiness;
    private int employeeCount;

    public BusinessLoan() {
        super();
        this.businessName = "";
        this.businessType = "";
        this.annualRevenue = 0.0;
        this.yearsInBusiness = 0;
        this.employeeCount = 0;
    }

    public BusinessLoan(String loanId, double loanAmount, double interestRate,
                      int loanDuration, String officerName, String branchLocation,
                      String businessName, String businessType, double annualRevenue,
                      int yearsInBusiness, int employeeCount) {
        super(loanId, "Business", loanAmount, interestRate, loanDuration, officerName, branchLocation);
        this.businessName = businessName;
        this.businessType = businessType;
        this.annualRevenue = annualRevenue;
        this.yearsInBusiness = yearsInBusiness;
        this.employeeCount = employeeCount;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public double getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public int getYearsInBusiness() {
        return yearsInBusiness;
    }

    public void setYearsInBusiness(int yearsInBusiness) {
        this.yearsInBusiness = yearsInBusiness;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public double calculateInterest() {
        double rate = getInterestRate() / 100;
        int months = getLoanDuration();
        double principal = getLoanAmount();
        double interest = principal * rate * (months / 12.0);
        if (yearsInBusiness < 2) {
            interest *= 1.25;
        }
        return interest;
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        if (!super.checkEligibility(customer)) {
            return false;
        }
        if (yearsInBusiness < 1) {
            return false;
        }
        double loanToRevenue = getLoanAmount() / annualRevenue;
        return loanToRevenue <= 0.3;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "  Business Name: " + businessName + "\n" +
               "  Business Type: " + businessType + "\n" +
               "  Annual Revenue: " + String.format("%.2f", annualRevenue) + "\n" +
               "  Years In Business: " + yearsInBusiness + "\n" +
               "  Employee Count: " + employeeCount;
    }
}