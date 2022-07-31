package BankingSystem;

import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;


public class ATM {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("Welcome to the ATM management System.");


        Session session = CreateSessionFactory.sessionFactory.openSession();
        Query query = session.createQuery("from ATMMachine");

        List queryResult = query.getResultList();

        for(Object row : queryResult){
            ATMMachine atmMachine = (ATMMachine) row;
            System.out.println(atmMachine.getAtmId()+"         "+atmMachine.getAtmLocation().toString()+"       "+atmMachine.isActive());
        }

        boolean menuControl = true;
        ATMMachine atmMachine = null;
        while (menuControl)
        {
            System.out.println("Please select which of the following ATM's you want to use using the atm id?");
            try{
                   int option = sc.nextInt();
                   atmMachine = session.get(ATMMachine.class, option);
                   menuControl = false;
                   session.close();
            }catch (Exception e){
                System.out.println("Please select a valid input");
            }
        }

        useAtm(atmMachine);





    }

    private static void useAtm(ATMMachine atmMachine) {
        boolean mainMenuControl = true;
        Session session = CreateSessionFactory.sessionFactory.openSession();
        while (mainMenuControl){
            System.out.println("WELCOME TO THE ATM. PLEASE CHOOSE THE FOLLOWING OPTIONS");
            System.out.println("1 to change your mPin\n2 to see your transaction History.\n" +
                    "3 to update your account details.\n4 to transfer funds into an account.\n5 to withdraw from your account.\n"+
                    "6 to pay your EMI.\n"+"99 to quit.");

            String option = sc.next();

            System.out.print("Please enter you name : ");
            sc.nextLine();
            String name = sc.nextLine();

            System.out.print("Please enter your account no :");
            long accountNo = sc.nextLong();

            boolean userExists = Util.authenticateUser(name, accountNo);

            if(!userExists)
                return;

            Account account = session.get(Account.class, accountNo);
            // atm machine uses atm cards.
            switch (option){

                case "1":
                    atmMachine.changePin(account.getAtmCard());
                    break;
                case "2":
                    atmMachine.transactionHistory(account.getAtmCard());
                    break;
                case "3":
                    atmMachine.updateAccountDetails(account.getAtmCard());
                    break;
                case "4":
                    atmMachine.transferFunds(account.getAtmCard());
                    break;
                case "5":
                    atmMachine.withdraw(account.getAtmCard());
                    break;
                case "6":
                    atmMachine.payLoanEmi(account.getAtmCard());
                    break;
                case "99":
                    mainMenuControl = false;
                    session.close();
                    break;
                default:
                    System.out.println("Please enter a valid input");
                    break;
            }

        }
    }
}
