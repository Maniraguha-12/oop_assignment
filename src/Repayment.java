import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Repayment {
    private String paymentId;
    private LoanManager loan;
    private double amountPaid;
    private LocalDate paymentDate;
    private double remainingBalance;

    public Repayment() {
        this.paymentId = "";
        this.loan = null;
        this.amountPaid = 0.0;
        this.paymentDate = LocalDate.now();
        this.remainingBalance = 0.0;
    }

    public Repayment(String paymentId, LoanManager loan, double amountPaid) {
        this.paymentId = paymentId;
        this.loan = loan;
        this.amountPaid = amountPaid;
        this.paymentDate = LocalDate.now();
        this.remainingBalance = loan != null ? loan.calculateRemainingBalance() : 0.0;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public LoanManager getLoan() {
        return loan;
    }

    public void setLoan(LoanManager loan) {
        this.loan = loan;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public void updateRemainingBalance() {
        if (loan != null) {
            this.remainingBalance = loan.calculateRemainingBalance();
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Repayment Details:\n" +
               "  Payment ID: " + paymentId + "\n" +
               "  Loan ID: " + (loan != null ? loan.getLoanId() : "N/A") + "\n" +
               "  Amount Paid: " + String.format("%.2f", amountPaid) + "\n" +
               "  Payment Date: " + paymentDate.format(formatter) + "\n" +
               "  Remaining Balance: " + String.format("%.2f", remainingBalance);
    }
}