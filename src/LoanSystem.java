import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class LoanSystem {
    private static Set<String> usedLoanIds = new HashSet<>();
    private static Set<String> usedCustomerIds = new HashSet<>();
    private static Scanner scanner = new Scanner(System.in);
    private static List<LoanManager> loanRegistry = new ArrayList<>();
    private static List<Customer> customerRegistry = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("    BANK OF KIGALI - LOAN MANAGEMENT SYSTEM      ");
        System.out.println("================================================");

        boolean continueSystem = true;
        while (continueSystem) {
            displayMainMenu();
            String choice = getValidInput("Select option", "[1-6]");

            switch (choice) {
                case "1":
                    applyForLoan();
                    break;
                case "2":
                    makePayment();
                    break;
                case "3":
                    viewLoanDetails();
                    break;
                case "4":
                    listAllLoans();
                    break;
                case "5":
                    checkEligibility();
                    break;
                case "6":
                    continueSystem = false;
                    System.out.println("\n" + "=".repeat(60));
                    System.out.println("    Thank you for using Bank of Kigali Loan System.");
                    System.out.println("    Have a great day!");
                    System.out.println("=".repeat(60));
                    break;
                default:
                    System.out.println("Invalid option. Please select 1-6.");
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("        BANK OF KIGALI - LOAN MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("\n[1]  Apply for New Loan");
        System.out.println("[2]  Make Loan Payment");
        System.out.println("[3]  View Loan Details & Reports");
        System.out.println("[4]  List All Loans");
        System.out.println("[5]  Check Loan Eligibility");
        System.out.println("[6]  Exit System");
        System.out.println("-".repeat(60));
    }

    private static String getValidInput(String prompt, String expectedFormat) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    private static String getValidInput(String prompt) {
        return getValidInput(prompt, "");
    }

    private static String getValidInputWithRetry(String prompt, String expectedFormat,
                                                   java.util.function.Function<String, String> validator) {
        String input;
        String error;
        do {
            System.out.print(prompt + ": ");
            input = scanner.nextLine().trim();
            error = validator.apply(input);
            if (error != null) {
                System.out.println("ERROR: " + error);
            }
        } while (error != null);
        return input;
    }

    private static void applyForLoan() {
        System.out.println("\n----------- APPLY FOR NEW LOAN ------------");

        String loanId = getValidInputWithRetry("Enter Loan ID (format: LNXXXXXX)",
            "unique ID", s -> {
                String err = InputValidator.validateLoanId(s);
                if (err != null) return err;
                if (usedLoanIds.contains(s)) return "Loan ID already exists.";
                return null;
            });

        System.out.println("Enter Customer Details:");
        String customerId = getValidInputWithRetry("Enter Customer ID (format: CUXXXXXX)",
            "unique ID", s -> {
                String err = InputValidator.validateCustomerId(s);
                if (err != null) return err;
                if (usedCustomerIds.contains(s)) return "Customer ID already exists.";
                return null;
            });

        String customerName = getValidInputWithRetry("Enter Customer Name",
            "text", InputValidator::validateEmpty);

        String nationalId = getValidInputWithRetry("Enter National ID",
            "alphanumeric", InputValidator::validateNationalId);

        String phoneNumber = getValidInputWithRetry("Enter Phone Number",
            "phone", InputValidator::validatePhoneNumber);

        Customer customer = new Customer(customerId, customerName, nationalId, phoneNumber);
        customerRegistry.add(customer);
        usedCustomerIds.add(customerId);

        System.out.println("\nAvailable Loan Types:");
        String[] validTypes = LoanFactory.getValidLoanTypes();
        for (int i = 0; i < validTypes.length; i++) {
            System.out.println((i + 1) + ". " + validTypes[i]);
        }

        String loanType = getValidInputWithRetry("Enter Loan Type",
            "loan type", InputValidator::validateLoanType);

        String loanAmount = getValidInputWithRetry("Enter Loan Amount",
            "positive number", input -> {
                String err = InputValidator.validatePositiveNumber(input, "Loan Amount");
                if (err != null) return err;
                double amt = Double.parseDouble(input);
                if (amt > 100000000) return "Loan Amount cannot exceed 100,000,000.";
                return null;
            });

        String interestRate = getValidInputWithRetry("Enter Interest Rate (%)",
            "interest rate", InputValidator::validateInterestRate);

        String duration = getValidInputWithRetry("Enter Loan Duration (months)",
            "1-360 months", InputValidator::validateLoanDuration);

        String officerName = getValidInputWithRetry("Enter Loan Officer Name",
            "text", InputValidator::validateEmpty);

        String branchLocation = getValidInputWithRetry("Enter Branch Location",
            "text", InputValidator::validateEmpty);

        String[] additionalFields = new String[0];
        if (loanType.equalsIgnoreCase("Personal")) {
            String purpose = getValidInputWithRetry("Enter Loan Purpose", "text", InputValidator::validateEmpty);
            String existingLoan = getValidInputWithRetry("Has Existing Loan? (true/false)", "boolean", 
                s -> s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false") ? null : "Enter true or false");
            String creditScore = getValidInputWithRetry("Enter Credit Score (300-850)", "integer",
                s -> { try { int cs = Integer.parseInt(s); return cs >= 300 && cs <= 850 ? null : "Credit score must be 300-850"; } catch (Exception e) { return "Enter a valid number"; } });
            additionalFields = new String[]{purpose, existingLoan, creditScore};
        } else if (loanType.equalsIgnoreCase("Home")) {
            String propType = getValidInput("Property Type (House/Apartment/Plot): ");
            String propValue = getValidInputWithRetry("Property Value", "positive", InputValidator::validatePositiveNumber);
            String address = getValidInput("Property Address: ");
            String newProp = getValidInputWithRetry("New Construction? (true/false)", "boolean",
                s -> s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false") ? null : "Enter true or false");
            additionalFields = new String[]{propType, propValue, address, newProp};
        } else if (loanType.equalsIgnoreCase("Car")) {
            String vType = getValidInput("Vehicle Type (Sedan/SUV/Truck): ");
            String vModel = getValidInput("Vehicle Model: ");
            String vValue = getValidInputWithRetry("Vehicle Value", "positive", InputValidator::validatePositiveNumber);
            String vYear = getValidInputWithRetry("Vehicle Year", "integer",
                s -> { try { int yr = Integer.parseInt(s); return yr >= 1990 && yr <= 2026 ? null : "Year must be 1990-2026"; } catch (Exception e) { return "Enter a valid year"; } });
            additionalFields = new String[]{vType, vModel, vValue, vYear};
        } else if (loanType.equalsIgnoreCase("Business")) {
            String bizName = getValidInputWithRetry("Business Name", "text", InputValidator::validateEmpty);
            String bizType = getValidInput("Business Type (Retail/Manufacturing/Service): ");
            String revenue = getValidInputWithRetry("Annual Revenue", "positive", InputValidator::validatePositiveNumber);
            String years = getValidInputWithRetry("Years In Business", "positive",
                s -> { try { int yr = Integer.parseInt(s); return yr >= 1 ? null : "Must be at least 1 year"; } catch (Exception e) { return "Enter a valid number"; } });
            String employees = getValidInputWithRetry("Number of Employees", "positive",
                s -> { try { int emp = Integer.parseInt(s); return emp >= 1 ? null : "Must be at least 1"; } catch (Exception e) { return "Enter a valid number"; } });
            additionalFields = new String[]{bizName, bizType, revenue, years, employees};
        } else if (loanType.equalsIgnoreCase("Student")) {
            String uni = getValidInputWithRetry("University Name", "text", InputValidator::validateEmpty);
            String degree = getValidInput("Degree Program: ");
            String year = getValidInputWithRetry("Academic Year (1-5)", "integer",
                s -> { try { int yr = Integer.parseInt(s); return yr >= 1 && yr <= 5 ? null : "Year must be 1-5"; } catch (Exception e) { return "Enter a valid number"; } });
            String guardian = getValidInput("Guardian Name: ");
            String gIncome = getValidInput("Guardian Income (optional, press enter for 0): ");
            if (gIncome.isEmpty()) gIncome = "0";
            additionalFields = new String[]{uni, degree, year, guardian, gIncome};
        } else if (loanType.equalsIgnoreCase("Agricultural")) {
            String farmType = getValidInput("Farm Type (Crop/Livestock/Mixed): ");
            String farmSize = getValidInputWithRetry("Farm Size (hectares)", "positive", InputValidator::validatePositiveNumber);
            String location = getValidInput("Farm Location: ");
            String crop = getValidInput("Primary Crop: ");
            String insurance = getValidInputWithRetry("Has Farm Insurance? (true/false)", "boolean",
                s -> s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false") ? null : "Enter true or false");
            additionalFields = new String[]{farmType, farmSize, location, crop, insurance};
        }

        LoanManager loan = LoanFactory.createLoan(
            loanType, loanId, Double.parseDouble(loanAmount),
            Double.parseDouble(interestRate), Integer.parseInt(duration),
            officerName, branchLocation, additionalFields
        );

        if (loan != null) {
            if (!loan.validateLoanDetails()) {
                System.out.println("\nERROR: Loan details are invalid.");
                return;
            }

            if (loan.checkEligibility(customer)) {
                loan.approveLoan();
                System.out.println("\n*** LOAN APPROVED! ***");
            } else {
                loan.rejectLoan();
                System.out.println("\n*** LOAN NOT APPROVED - Does not meet eligibility criteria ***");
            }

            usedLoanIds.add(loanId);
            loanRegistry.add(loan);

            System.out.println("\n" + customer.toString());
            System.out.println("\n" + loan.toString());
            System.out.println("\n" + loan.generateLoanReport());
        } else {
            System.out.println("\nERROR: Failed to create loan. Please try again.");
        }
    }

    private static void makePayment() {
        System.out.println("\n---------- MAKE PAYMENT -------------");
        String loanId = getValidInput("Enter Loan ID: ");

        LoanManager loan = findLoanById(loanId);
        if (loan == null) {
            System.out.println("ERROR: Loan not found. Please check the Loan ID.");
            return;
        }

        if (!loan.getLoanStatus().equalsIgnoreCase("Approved")) {
            System.out.println("ERROR: Loan is not approved. Cannot accept payments for: " + loan.getLoanStatus());
            return;
        }

        System.out.println("Payment processing for loan: " + loanId);
        System.out.println("Current Status: " + loan.getLoanStatus());
        System.out.println("Remaining Balance: " + String.format("%.2f", loan.calculateRemainingBalance()));

        if (loan.calculateRemainingBalance() <= 0) {
            System.out.println("Loan is already fully paid.");
            return;
        }

        String amount = getValidInputWithRetry("Enter Payment Amount",
            "positive number", InputValidator::validatePositiveNumber);

        double paymentAmount = Double.parseDouble(amount);
        String paymentId = "PAY" + System.currentTimeMillis();

        if (loan.processPayment(paymentAmount)) {
            System.out.println("\n*** PAYMENT SUCCESSFUL ***");
            Repayment repayment = new Repayment(paymentId, loan, paymentAmount);
            System.out.println(repayment.toString());
            System.out.println(loan.generatePaymentReceipt());
        } else {
            System.out.println("\n*** PAYMENT FAILED ***");
            System.out.println("Payment amount exceeds remaining balance or is invalid.");
        }
    }

    private static LoanManager findLoanById(String loanId) {
        for (LoanManager loan : loanRegistry) {
            if (loan.getLoanId().equalsIgnoreCase(loanId)) {
                return loan;
            }
        }
        return null;
    }

    private static void viewLoanDetails() {
        System.out.println("\n---------- VIEW LOAN DETAILS ---------");
        String loanId = getValidInput("Enter Loan ID: ");

        LoanManager loan = findLoanById(loanId);
        if (loan == null) {
            System.out.println("ERROR: Loan not found.");
            return;
        }

        System.out.println(loan.generateLoanReport());
    }

    private static void listAllLoans() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           REGISTERED LOANS SUMMARY");
        System.out.println("=".repeat(60));

        if (loanRegistry.isEmpty()) {
            System.out.println("No loans registered in the system.");
            System.out.println("=".repeat(60));
            return;
        }

        System.out.printf("%-10s %-12s %-15s %-12s %-12s\n",
            "Loan ID", "Type", "Amount", "Status", "Customer");
        System.out.println("-".repeat(60));

        for (LoanManager loan : loanRegistry) {
            System.out.printf("%-10s %-12s %-15s %-12s %-12s\n",
                loan.getLoanId(),
                loan.getLoanType(),
                String.format("$%.2f", loan.getLoanAmount()),
                loan.getLoanStatus(),
                getCustomerNameForLoan(loan.getLoanId()));
        }
        System.out.println("=".repeat(60));
        System.out.println("Total Loans: " + loanRegistry.size());
    }

    private static String getCustomerNameForLoan(String loanId) {
        return "N/A";
    }

    private static void checkEligibility() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           LOAN ELIGIBILITY CHECKER");
        System.out.println("=".repeat(60));

        String customerId = getValidInput("Enter Customer ID to check eligibility");
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("ERROR: Customer not found.");
            return;
        }

        System.out.println("\nCustomer: " + customer.getCustomerName());
        System.out.println("\nAvailable Loan Types for Eligibility Check:");
        String[] validTypes = LoanFactory.getValidLoanTypes();
        for (int i = 0; i < validTypes.length; i++) {
            System.out.println("  " + (i + 1) + ". " + validTypes[i]);
        }

        String loanType = getValidInput("Select loan type to check");
        System.out.println("\n--- Eligibility Result ---");
        LoanManager dummyLoan = new LoanManager("TEMP", loanType, 50000, 15.0, 24, "Officer", "Branch");
        boolean eligible = dummyLoan.checkEligibility(customer);
        System.out.println("Customer " + customer.getCustomerName() + " is " +
            (eligible ? "ELIGIBLE" : "NOT ELIGIBLE") + " for a " + loanType + " loan.");
    }

    private static Customer findCustomerById(String customerId) {
        for (Customer customer : customerRegistry) {
            if (customer.getCustomerId().equalsIgnoreCase(customerId)) {
                return customer;
            }
        }
        return null;
    }
}