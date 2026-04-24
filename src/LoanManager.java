import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LoanManager extends Loan implements Payable {
    private String officerName;
    private String branchLocation;
    private double amountPaid;
    private LocalDate lastPaymentDate;

    public LoanManager() {
        super();
        this.officerName = "";
        this.branchLocation = "";
        this.amountPaid = 0.0;
        this.lastPaymentDate = null;
    }

    public LoanManager(String loanId, String loanType, double loanAmount,
                     double interestRate, int loanDuration,
                     String officerName, String branchLocation) {
        super(loanId, loanType, loanAmount, interestRate, loanDuration);
        this.officerName = officerName;
        this.branchLocation = branchLocation;
        this.amountPaid = 0.0;
        this.lastPaymentDate = null;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return super.toString() + "\n" +
               "  Officer Name: " + officerName + "\n" +
               "  Branch Location: " + branchLocation + "\n" +
               "  Amount Paid: " + String.format("%.2f", amountPaid) + "\n" +
               "  Last Payment: " + (lastPaymentDate != null ? lastPaymentDate.format(formatter) : "N/A");
    }

    @Override
    public double calculateInterest() {
        double principal = getLoanAmount();
        double rate = getInterestRate() / 100;
        int months = getLoanDuration();
        return (principal * rate * months) / 12;
    }

    @Override
    public double calculateMonthlyInstallment() {
        double totalRepayment = calculateTotalRepayment();
        int months = getLoanDuration();
        if (months <= 0) {
            return 0.0;
        }
        return totalRepayment / months;
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        if (customer == null) {
            return false;
        }

        double monthlyInstallment = calculateMonthlyInstallment();
        if (monthlyInstallment <= 0) {
            return false;
        }

        double requiredIncome = monthlyInstallment * 3;
        return requiredIncome > 0;
    }

    @Override
    public boolean approveLoan() {
        if (getLoanAmount() <= 0 || getInterestRate() <= 0 || getLoanDuration() <= 0) {
            return false;
        }
        setLoanStatus("Approved");
        return true;
    }

    @Override
    public boolean rejectLoan() {
        setLoanStatus("Rejected");
        return true;
    }

    @Override
    public double calculateTotalRepayment() {
        double principal = getLoanAmount();
        double interest = calculateInterest();
        return principal + interest;
    }

    @Override
    public String generateLoanReport() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder report = new StringBuilder();
        report.append("======================================\n");
        report.append("         LOAN REPORT                \n");
        report.append("======================================\n");
        report.append(super.toString()).append("\n");
        report.append("  Officer Name: ").append(officerName).append("\n");
        report.append("  Branch Location: ").append(branchLocation).append("\n");
        report.append("  Interest Amount: ").append(String.format("%.2f", calculateInterest())).append("\n");
        report.append("  Total Repayment: ").append(String.format("%.2f", calculateTotalRepayment())).append("\n");
        report.append("  Monthly Installment: ").append(String.format("%.2f", calculateMonthlyInstallment())).append("\n");
        report.append("  Amount Paid: ").append(String.format("%.2f", amountPaid)).append("\n");
        report.append("  Remaining Balance: ").append(String.format("%.2f", calculateRemainingBalance())).append("\n");
        report.append("======================================\n");
        return report.toString();
    }

    @Override
    public boolean validateLoanDetails() {
        if (getLoanId() == null || getLoanId().isEmpty()) {
            return false;
        }
        if (getLoanType() == null || getLoanType().isEmpty()) {
            return false;
        }
        if (getLoanAmount() <= 0) {
            return false;
        }
        if (getInterestRate() < 0 || getInterestRate() > 100) {
            return false;
        }
        if (getLoanDuration() <= 0 || getLoanDuration() > 360) {
            return false;
        }
        return true;
    }

    @Override
    public boolean processPayment(double amount) {
        if (amount <= 0) {
            return false;
        }
        double remaining = calculateRemainingBalance();
        if (amount > remaining) {
            return false;
        }
        amountPaid += amount;
        lastPaymentDate = LocalDate.now();
        return true;
    }

    @Override
    public double calculateRemainingBalance() {
        double total = calculateTotalRepayment();
        return total - amountPaid;
    }

    @Override
    public String generatePaymentReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder receipt = new StringBuilder();
        receipt.append("======================================\n");
        receipt.append("       PAYMENT RECEIPT            \n");
        receipt.append("======================================\n");
        receipt.append("  Loan ID: ").append(getLoanId()).append("\n");
        receipt.append("  Loan Type: ").append(getLoanType()).append("\n");
        receipt.append("  Payment Date: ").append(LocalDate.now().format(formatter)).append("\n");
        receipt.append("  Amount Paid: ").append(String.format("%.2f", amountPaid)).append("\n");
        receipt.append("  Remaining Balance: ").append(String.format("%.2f", calculateRemainingBalance())).append("\n");
        receipt.append("======================================\n");
        return receipt.toString();
    }
}