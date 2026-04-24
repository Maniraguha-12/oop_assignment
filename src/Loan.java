import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Loan {
    private String loanId;
    private String loanType;
    private double loanAmount;
    private double interestRate;
    private int loanDuration;
    private String loanStatus;
    private LocalDate approvalDate;

    public Loan() {
        this.loanId = "";
        this.loanType = "";
        this.loanAmount = 0.0;
        this.interestRate = 0.0;
        this.loanDuration = 0;
        this.loanStatus = "Pending";
        this.approvalDate = LocalDate.now();
    }

    public Loan(String loanId, String loanType, double loanAmount, 
               double interestRate, int loanDuration) {
        this.loanId = loanId;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanDuration = loanDuration;
        this.loanStatus = "Pending";
        this.approvalDate = LocalDate.now();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Loan Details:\n" +
               "  Loan ID: " + loanId + "\n" +
               "  Loan Type: " + loanType + "\n" +
               "  Loan Amount: " + String.format("%.2f", loanAmount) + "\n" +
               "  Interest Rate: " + interestRate + "%\n" +
               "  Duration: " + loanDuration + " months\n" +
               "  Status: " + loanStatus + "\n" +
               "  Approval Date: " + (approvalDate != null ? approvalDate.format(formatter) : "N/A");
    }

    public abstract double calculateInterest();

    public abstract double calculateMonthlyInstallment();

    public abstract boolean checkEligibility(Customer customer);

    public abstract boolean approveLoan();

    public abstract boolean rejectLoan();

    public abstract double calculateTotalRepayment();

    public abstract String generateLoanReport();

    public abstract boolean validateLoanDetails();
}