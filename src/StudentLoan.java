public class StudentLoan extends LoanManager {
    private String universityName;
    private String degreeProgram;
    private int academicYear;
    private String guardianName;
    private double guardianIncome;

    public StudentLoan() {
        super();
        this.universityName = "";
        this.degreeProgram = "";
        this.academicYear = 0;
        this.guardianName = "";
        this.guardianIncome = 0.0;
    }

    public StudentLoan(String loanId, double loanAmount, double interestRate,
                       int loanDuration, String officerName, String branchLocation,
                       String universityName, String degreeProgram, int academicYear,
                       String guardianName, double guardianIncome) {
        super(loanId, "Student", loanAmount, interestRate, loanDuration, officerName, branchLocation);
        this.universityName = universityName;
        this.degreeProgram = degreeProgram;
        this.academicYear = academicYear;
        this.guardianName = guardianName;
        this.guardianIncome = guardianIncome;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(String degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    public int getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public double getGuardianIncome() {
        return guardianIncome;
    }

    public void setGuardianIncome(double guardianIncome) {
        this.guardianIncome = guardianIncome;
    }

    @Override
    public double calculateInterest() {
        double rate = getInterestRate() / 100;
        int months = getLoanDuration();
        double principal = getLoanAmount();
        double interest = principal * rate * (months / 12.0);
        if (guardianIncome > 0) {
            interest *= 0.85;
        }
        return interest;
    }

    @Override
    public boolean checkEligibility(Customer customer) {
        if (!super.checkEligibility(customer)) {
            return false;
        }
        return academicYear >= 1 && academicYear <= 5;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "  University: " + universityName + "\n" +
               "  Degree Program: " + degreeProgram + "\n" +
               "  Academic Year: " + academicYear + "\n" +
               "  Guardian Name: " + guardianName + "\n" +
               "  Guardian Income: " + String.format("%.2f", guardianIncome);
    }
}