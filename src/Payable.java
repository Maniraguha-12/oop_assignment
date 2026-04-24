public interface Payable {
    boolean processPayment(double amount);

    double calculateRemainingBalance();

    String generatePaymentReceipt();
}