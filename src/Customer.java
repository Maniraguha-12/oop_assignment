public class Customer {
    private String customerId;
    private String customerName;
    private String nationalId;
    private String phoneNumber;

    public Customer() {
        this.customerId = "";
        this.customerName = "";
        this.nationalId = "";
        this.phoneNumber = "";
    }

    public Customer(String customerId, String customerName, String nationalId, String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer Details:\n" +
               "  Customer ID: " + customerId + "\n" +
               "  Customer Name: " + customerName + "\n" +
               "  National ID: " + nationalId + "\n" +
               "  Phone Number: " + phoneNumber;
    }
}