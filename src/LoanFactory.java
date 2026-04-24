public class LoanFactory {
    public static LoanManager createLoan(String loanType, String loanId, double loanAmount,
                                          double interestRate, int loanDuration,
                                          String officerName, String branchLocation,
                                          String[] additionalFields) {
        switch (loanType.toLowerCase()) {
            case "personal":
                String purpose = additionalFields.length > 0 ? additionalFields[0] : "";
                boolean hasExisting = additionalFields.length > 1 ? 
                    Boolean.parseBoolean(additionalFields[1]) : false;
                int creditScore = additionalFields.length > 2 ? 
                    Integer.parseInt(additionalFields[2]) : 650;
                return new PersonalLoan(loanId, loanAmount, interestRate, loanDuration,
                                         officerName, branchLocation, purpose, hasExisting, creditScore);

            case "home":
                String propertyType = additionalFields.length > 0 ? additionalFields[0] : "Apartment";
                double propertyValue = additionalFields.length > 1 ? 
                    Double.parseDouble(additionalFields[1]) : loanAmount * 1.2;
                String propertyAddress = additionalFields.length > 2 ? additionalFields[2] : "";
                boolean isNew = additionalFields.length > 3 ? 
                    Boolean.parseBoolean(additionalFields[3]) : false;
                return new HomeLoan(loanId, loanAmount, interestRate, loanDuration,
                                  officerName, branchLocation, propertyType, propertyValue,
                                  propertyAddress, isNew);

            case "car":
                String vehicleType = additionalFields.length > 0 ? additionalFields[0] : "Sedan";
                String vehicleModel = additionalFields.length > 1 ? additionalFields[1] : "";
                double vehicleValue = additionalFields.length > 2 ?
                    Double.parseDouble(additionalFields[2]) : loanAmount * 1.1;
                int vehicleYear = additionalFields.length > 3 ?
                    Integer.parseInt(additionalFields[3]) : 2020;
                return new CarLoan(loanId, loanAmount, interestRate, loanDuration,
                                 officerName, branchLocation, vehicleType, vehicleModel,
                                 vehicleValue, vehicleYear);

            case "business":
                String businessName = additionalFields.length > 0 ? additionalFields[0] : "";
                String businessType = additionalFields.length > 1 ? additionalFields[1] : "Retail";
                double annualRevenue = additionalFields.length > 2 ? 
                    Double.parseDouble(additionalFields[2]) : loanAmount * 4;
                int yearsInBusiness = additionalFields.length > 3 ? 
                    Integer.parseInt(additionalFields[3]) : 2;
                int employeeCount = additionalFields.length > 4 ? 
                    Integer.parseInt(additionalFields[4]) : 5;
                return new BusinessLoan(loanId, loanAmount, interestRate, loanDuration,
                                      officerName, branchLocation, businessName, businessType,
                                      annualRevenue, yearsInBusiness, employeeCount);

            case "student":
                String universityName = additionalFields.length > 0 ? additionalFields[0] : "";
                String degreeProgram = additionalFields.length > 1 ? additionalFields[1] : "";
                int academicYear = additionalFields.length > 2 ? 
                    Integer.parseInt(additionalFields[2]) : 1;
                String guardianName = additionalFields.length > 3 ? additionalFields[3] : "";
                double guardianIncome = additionalFields.length > 4 ? 
                    Double.parseDouble(additionalFields[4]) : 0;
                return new StudentLoan(loanId, loanAmount, interestRate, loanDuration,
                                   officerName, branchLocation, universityName, degreeProgram,
                                   academicYear, guardianName, guardianIncome);

            case "agricultural":
                String farmType = additionalFields.length > 0 ? additionalFields[0] : "Crop";
                double farmSize = additionalFields.length > 1 ? 
                    Double.parseDouble(additionalFields[1]) : 1.0;
                String farmLocation = additionalFields.length > 2 ? additionalFields[2] : "";
                String cropType = additionalFields.length > 3 ? additionalFields[3] : "";
                boolean hasInsurance = additionalFields.length > 4 ? 
                    Boolean.parseBoolean(additionalFields[4]) : false;
                return new AgriculturalLoan(loanId, loanAmount, interestRate, loanDuration,
                                         officerName, branchLocation, farmType, farmSize,
                                         farmLocation, cropType, hasInsurance);

            default:
                return null;
        }
    }

    public static String[] getLoanTypeFields(String loanType) {
        switch (loanType.toLowerCase()) {
            case "personal":
                return new String[]{"purpose", "hasExistingLoan", "creditScore"};
            case "home":
                return new String[]{"propertyType", "propertyValue", "propertyAddress", "isNewConstruction"};
            case "car":
                return new String[]{"vehicleType", "vehicleModel", "vehicleValue", "vehicleYear"};
            case "business":
                return new String[]{"businessName", "businessType", "annualRevenue", "yearsInBusiness", "employeeCount"};
            case "student":
                return new String[]{"universityName", "degreeProgram", "academicYear", "guardianName", "guardianIncome"};
            case "agricultural":
                return new String[]{"farmType", "farmSize", "farmLocation", "cropType", "hasFarmInsurance"};
            default:
                return new String[]{};
        }
    }

    public static String[] getValidLoanTypes() {
        return new String[]{"Personal", "Home", "Car", "Business", "Student", "Agricultural"};
    }
}