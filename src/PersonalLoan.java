public class PersonalLoan extends LoanManager {
    private String purpose;
    private boolean hasExistingLoan;
    private int creditScore;

    public PersonalLoan() {
        super();
        this.purpose = "";
        this.hasExistingLoan = false;
        this.creditScore = 0;
    }

    public PersonalLoan(String loanId, double loanAmount, double interestRate,
                        int loanDuration, String officerName, String branchLocation,
                        String purpose, boolean hasExistingLoan, int creditScore) {
        super(loanId, "Personal", loanAmount, interestRate, loanDuration, officerName, branchLocation);
        this.purpose = purpose;
        this.hasExistingLoan = hasExistingLoan;
        this.creditScore = creditScore;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public boolean isHasExistingLoan() {
        return hasExistingLoan;
    }

    public void setHasExistingLoan(boolean hasExistingLoan) {
        this.hasExistingLoan = hasExistingLoan;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public double calculateInterest() {
        double rate = getInterestRate() / 100;
        int months = getLoanDuration();
        double principal = getLoanAmount();
        double interest = principal * rate * (months / 12.0);
        if (hasExistingLoan) {
            interest *= 1.1;
        }
        return interest;
    }

    @Override
    public double calculateMonthlyInstallment() {
        return super.calculateMonthlyInstallment();
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        if (!super.checkEligibility(customer)) {
            return false;
        }
        return creditScore >= 600;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "  Purpose: " + purpose + "\n" +
               "  Has Existing Loan: " + hasExistingLoan + "\n" +
               "  Credit Score: " + creditScore;
    }
}