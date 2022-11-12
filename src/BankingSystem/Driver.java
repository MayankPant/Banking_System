package BankingSystem;

import java.util.Scanner;


public class Driver {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        produceMainMenu();
    }

    private static void produceMainMenu() {
        boolean mainMenuControl = true;

        while (mainMenuControl){
            System.out.println("WELCOME TO THE BANKING SYSTEM. PLEASE CHOOSE THE FOLLOWING OPTIONS");
            System.out.println("1 for Banking Management System.\n2 for Loan Management System.\n" +
                    "99 to quit.");
            String option = sc.next();

            switch (option){
                case "1":
                    produceBankingMenu();
                    break;
                case "2":
                    produceLoanMenu();
                    break;
                case "99":
                    System.out.println("Thanks for using our bank. Have a great day!");
                    mainMenuControl = false;
                    break;
                default:
                    System.out.println("Please enter a valid input or press 9 to quit.");
                    break;
            }
        }

        CreateSessionFactory.sessionFactory.close();
    }

    private static void produceBankingMenu() {
        boolean mainMenuControl = true;

        while (mainMenuControl){
            System.out.println("WELCOME TO THE BANKING SYSTEM. PLEASE CHOOSE THE FOLLOWING OPTIONS");
            System.out.println("1 to create an account\n2 to remove your existing account.\n" +
                    "3 to update your account details.\n4 to transfer funds into an account.\n5 to withdraw or deposit to your account.\n"+
                    "6 to view your transaction history.\n7 to take a bank loan.\n8 to pay your EMI.\n"+"99 to quit.");

            String option = sc.next();

            if(option.equals("1")){
                System.out.println("Welcome to account creation form.");
                System.out.println("Please enter your name.");
                sc.nextLine();
                String userName = sc.nextLine();
                sc.nextLine();
                System.out.println("Please enter your phone No");
                String phoneNo = sc.nextLine();
                sc.nextLine();
                System.out.println("Please enter your email address.");
                String email = sc.nextLine();
                sc.nextLine();
                System.out.println("Please enter a Nominee for your account.");
                String nominee = sc.nextLine();
                sc.nextLine();
                BankingManagementSystem.createAccount(userName, phoneNo, email, nominee);
            }
            else{
                if (option.equals("99"))
                {
                    System.out.println("Thanks for using our bank. Have a great day!");
                    mainMenuControl = false;
                    continue;
                }
                System.out.println("Welcome.");
                System.out.println("Please enter your name.");
                String userName = sc.nextLine();
                sc.nextLine();
                System.out.println("Please enter your account no");
                // case handled only here because in case of long the user can enter something else which could result in input mismatch
                long accountNo;
                try {
                    accountNo = sc.nextLong();
                    } catch (Exception e) {
                    System.out.println("Please enter a valid input.");
                    continue; // bypasses the rest
                }
                switch (option){
                    case "2":
                        BankingManagementSystem.removeAccount(userName,accountNo);
                        break;
                    case "3":
                        BankingManagementSystem.updateAccount(userName,accountNo);
                        break;
                    case "4":
                        BankingManagementSystem.transferFunds(userName,accountNo);
                        break;
                    case "5":
                        BankingManagementSystem.withdrawOrDeposit(userName,accountNo);
                        break;
                    case "6":
                        BankingManagementSystem.transactionHistory(userName,accountNo);
                        break;
                    case "7":
                        BankingManagementSystem.takeLoan(userName,accountNo);
                        break;
                    case "8":
                        BankingManagementSystem.payLoanEmi(userName,accountNo);
                        break;
                    default:
                        System.out.println("Please enter a valid input or press 99 to quit.");
                        break;
                }
            }

        }

    }

    private static void produceLoanMenu() {
        boolean menuControl = true;

        while (menuControl){
            System.out.println("WELCOME TO THE BANKING SYSTEM. PLEASE CHOOSE THE FOLLOWING OPTIONS");
            System.out.println("1 to take a loan.\n2 to pay your emi.\n" +
                    "99 to quit\n");

            String option = sc.next();
            if (option.equals("99"))
            {
                System.out.println("Thanks for using our bank. Have a great day!");
                menuControl = false;
                continue;
            }
            System.out.println("Welcome.");
            System.out.println("Please enter your name.");
            String userName = sc.nextLine();
            sc.nextLine();
            System.out.println("Please enter your account no");
            // case handled only here because in case of long the user can enter something else which could result in input mismatch
            long accountNo;
            try {
                    accountNo = sc.nextLong();
                    BankingManagementSystem.removeAccount(userName, accountNo);
            } catch (Exception e) {
                    System.out.println("Please enter a valid input.");
                    continue; // bypasses the rest
            }
            switch (option){
            case "1":
                BankingManagementSystem.takeLoan(userName,accountNo);
                break;
            case "2":
                BankingManagementSystem.payLoanEmi(userName,accountNo);
                break;
            default:
                System.out.println("Please enter a valid input or press 99 to quit.");
                break;
            }

        }

    }

}

