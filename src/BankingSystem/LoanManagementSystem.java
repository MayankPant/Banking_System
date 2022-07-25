package BankingSystem;

import java.util.GregorianCalendar;
import java.util.Scanner;

public class LoanManagementSystem {

    private static Scanner sc = new Scanner(System.in);

    public static Loan takeLoan(String name, long accountNo){

        boolean userExists = Util.authenticateUser(name, accountNo);

        if(!userExists)
            return null;

        System.out.println("Please enter your salary per annum here.");

        double salaryPerAnnum = sc.nextDouble();
        final double maximumPossibleLoan = 0.8 * salaryPerAnnum;

        System.out.println("You can at max get a loan of "+maximumPossibleLoan+" at this current salary");
        System.out.println("Please choose ahead.");
        short option = Util.twoOptionMenu("Continue", "Quit");

        if(option == 2){
            return null;
        }
        else {
            return getLoan(maximumPossibleLoan);
        }

    }

    private static Loan getLoan(double maximumPossibleLoan) {
        System.out.println("Please enter your loan amount.");
        double loanAmount = sc.nextDouble();
        if(loanAmount >= maximumPossibleLoan)
        {
            System.out.println("Please enter a value less than "+maximumPossibleLoan);
            return getLoan(maximumPossibleLoan);
        }
        else {
            System.out.println("Please enter what kind of a loan you want to take.");
            short interest = showTypesOfLoans();

            System.out.println("Please enter the tenure in months.");
            int tenure = sc.nextInt();

            System.out.println("You have applied for a loan of "+loanAmount+" at "+interest+" % interest rate for " + tenure + " months.\n" +
                    "Do you want to proceed?");

            short option = Util.twoOptionMenu("Yes","No");

            if(option == 2){// does not want to proceed here
                return null;
            }
            else{
                double emi = calculateEmi(loanAmount, interest, tenure);
            // account is null to omit creating a different session here. This loan has to be integrated to someones account by the calling class itself.
                Loan loan = new Loan(loanAmount, null, Math.round(emi), new GregorianCalendar(),0, tenure);
                return loan;
            }

        }
    }
    private static double calculateEmi(double loanAmount, short interest, int tenure) {
        double p = loanAmount;
        double r = (double) interest/(12 * 100);
        int n = tenure;
        // formula for emi calculation taken from internet. This is tested as well.
        double emi = p * r * Math.pow((1 + r),tenure)/(Math.pow((1 + r),n)-1);

        return emi;
    }

    private static short showTypesOfLoans() {
        System.out.println("1 for home loan at 5 % interest.\n2 for business loan at 7 % interest." +
                "\n3 for Education Loan at 10 % interest.\n4 for Personal Loan at 12 % interest.");
        String option = sc.next();

        switch (option)
        {
            case "1":
                return 5;
            case "2":
                return 7;
            case "3":
                return 10;
            case "4":
                return 12;
            default:
                System.out.println("Please enter a valid input");
                return showTypesOfLoans();
        }
    }
    public  static  void payLoanEmi(Account account){
        // the status of this account has to be persisted after this call.
        boolean userExists  = Util.authenticateUser(account.getName(), account.getAccountNo());

        if(!userExists)
            return;
        Loan loan = account.getLoan();

        System.out.println("Select the medium to pay your emi in.");
        short option  = Util.twoOptionMenu("Cash","Saving Account");
        if(option == 1){
            // the user gives his cash to the bank or atm Machine
            loan.setRemainingEMIs(loan.getRemainingEMIs() - 1);
            loan.setDoneEMIs(loan.getDoneEMIs() + 1);
            loan.setLoanAmount(loan.getLoanAmount()  - loan.getEmi());
            // if you don't have any remaining emi's then you loan is paid.

            if(loan.getRemainingEMIs() == 0)
                account.setLoan(null);


            Transaction transaction = new Transaction(Constants.EMI_PAID, account,new GregorianCalendar(),loan.getEmi(),loan);
            account.getTransactionHistory().add(transaction);
            System.out.println(transaction.getTransactionDescription());
            account.setLoan(loan);
        }
        else{
            if(account.getBalance() >= loan.getEmi()){

                loan.setRemainingEMIs(loan.getRemainingEMIs() - 1);
                loan.setDoneEMIs(loan.getDoneEMIs() + 1);
                loan.setLoanAmount(loan.getLoanAmount()  - loan.getEmi());

                // the amount is deducted from his bank balance.
                account.setBalance(account.getBalance() - loan.getEmi());

                // if you don't have any remaining emi's then you loan is paid.

                if(loan.getRemainingEMIs() == 0)
                    account.setLoan(null);



                Transaction transaction = new Transaction(Constants.EMI_PAID, account,new GregorianCalendar(),loan.getEmi(),loan);
                account.getTransactionHistory().add(transaction);
                System.out.println(transaction.getTransactionDescription());
                account.setLoan(loan);

            }
            else{
                System.out.println("You don't have enough balance left in your account.");
            }
        }

    }
}