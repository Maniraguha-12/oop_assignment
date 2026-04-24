import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final Pattern NATIONAL_ID_PATTERN = Pattern.compile("^[A-Z0-9]{8,20}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern LOAN_ID_PATTERN = Pattern.compile("^LN[0-9]{6,10}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern CUSTOMER_ID_PATTERN = Pattern.compile("^CU[0-9]{6,10}$", Pattern.CASE_INSENSITIVE);

    public static String validateEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            return fieldName + " cannot be empty.";
        }
        return null;
    }

    public static String validateNumber(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            return fieldName + " cannot be empty.";
        }
        try {
            Double.parseDouble(input);
            return null;
        } catch (NumberFormatException e) {
            return fieldName + " must be a valid number.";
        }
    }

    public static String validateInteger(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            return fieldName + " cannot be empty.";
        }
        try {
            Integer.parseInt(input);
            return null;
        } catch (NumberFormatException e) {
            return fieldName + " must be a valid integer.";
        }
    }

    public static String validatePositiveNumber(String input, String fieldName) {
        String numError = validateNumber(input, fieldName);
        if (numError != null) {
            return numError;
        }
        double value = Double.parseDouble(input);
        if (value <= 0) {
            return fieldName + " must be greater than zero.";
        }
        return null;
    }

    public static String validatePositiveInteger(String input, String fieldName) {
        String intError = validateInteger(input, fieldName);
        if (intError != null) {
            return intError;
        }
        int value = Integer.parseInt(input);
        if (value <= 0) {
            return fieldName + " must be greater than zero.";
        }
        return null;
    }

    public static String validateNegativeCheck(String input, String fieldName) {
        String numError = validateNumber(input, fieldName);
        if (numError != null) {
            return numError;
        }
        double value = Double.parseDouble(input);
        if (value < 0) {
            return fieldName + " cannot be negative.";
        }
        return null;
    }

    public static String validateInterestRate(String input) {
        String numError = validateNumber(input, "Interest Rate");
        if (numError != null) {
            return numError;
        }
        double rate = Double.parseDouble(input);
        if (rate < 0 || rate > 50) {
            return "Interest Rate must be between 0 and 50 percent.";
        }
        return null;
    }

    public static String validateLoanDuration(String input) {
        String intError = validateInteger(input, "Loan Duration");
        if (intError != null) {
            return intError;
        }
        int duration = Integer.parseInt(input);
        if (duration <= 0 || duration > 360) {
            return "Loan Duration must be between 1 and 360 months.";
        }
        return null;
    }

    public static String validatePhoneNumber(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Phone number cannot be empty.";
        }
        String cleaned = input.replaceAll("[\\s-]", "");
        if (!PHONE_PATTERN.matcher(cleaned).matches()) {
            return "Invalid phone number format. Use format: +2507XXXXXXXX or 07XXXXXXXX";
        }
        return null;
    }

    public static String validateNationalId(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "National ID cannot be empty.";
        }
        if (!NATIONAL_ID_PATTERN.matcher(input).matches()) {
            return "Invalid National ID format. Use 8-20 alphanumeric characters.";
        }
        return null;
    }

    public static String validateLoanId(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Loan ID cannot be empty.";
        }
        if (!LOAN_ID_PATTERN.matcher(input).matches()) {
            return "Invalid Loan ID format. Use format: LNXXXXXX";
        }
        return null;
    }

    public static String validateCustomerId(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Customer ID cannot be empty.";
        }
        if (!CUSTOMER_ID_PATTERN.matcher(input).matches()) {
            return "Invalid Customer ID format. Use format: CUXXXXXX";
        }
        return null;
    }

    public static String validateEmpty(String input) {
        return validateEmpty(input, "Input");
    }

    public static String validatePositiveNumber(String input) {
        return validatePositiveNumber(input, "Number");
    }

    public static String validateLoanType(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Loan type cannot be empty.";
        }
        String[] validTypes = {"Personal", "Home", "Car", "Business", "Student", "Agricultural"};
        boolean valid = false;
        for (String type : validTypes) {
            if (type.equalsIgnoreCase(input.trim())) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            return "Invalid loan type. Valid types: Personal, Home, Car, Business, Student, Agricultural";
        }
        return null;
    }

    public static String validateMaxAmount(String input, double maxAmount) {
        String numError = validatePositiveNumber(input, "Loan Amount");
        if (numError != null) {
            return numError;
        }
        double value = Double.parseDouble(input);
        if (value > maxAmount) {
            return "Loan Amount cannot exceed " + String.format("%.2f", maxAmount);
        }
        return null;
    }
}