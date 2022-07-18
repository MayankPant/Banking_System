package BankingSystem;

import org.hibernate.Session;

import java.util.Date;
import java.util.Scanner;

public class BankingManagementSystem {

    static Scanner sc = new Scanner(System.in);

    public static void createAccount(String name, String phoneNo, String email, String nominee)
    {
        long balance = -1;
        // providing an option for zero balance account
        System.out.println("Do you want to create a zero balance account? Press Y for yes and an N for a No");
        char option = sc.next().charAt(0);
        boolean menuControl = true;

        while (menuControl)
        {
            switch (option)
            {
                case 'Y':
                    balance = 0;
                    menuControl = false;
                    break;
                case 'N':
                    System.out.println("Enter the amount you want to deposit to open your account.");
                    menuControl  = false;
                    balance = sc.nextLong();
                    break;
                default:
                    System.out.println("Please enter a valid option or type \"QUIT\" to exit.");
                    String quitOption = sc.next();
                    if(quitOption.equals("QUIT"))
                    {
                        System.out.println("Thanks for working with us.");
                        menuControl =  false;
                        break;
                    }
                    break;
            }
        }

        Account account = new Account(name,phoneNo,email,nominee,balance);
        Transaction transaction = new Transaction(Constants.CREATE, account,new Date(),balance);
        account.getTransactionHistory().add(transaction);
        System.out.println("Your account has been created with account no "+account.getAccountNo());
        System.out.println(transaction.getTransactionDescription()); // always carries the last transaction


        try{
            Session session = CreateSessionFactory.sessionFactory.openSession();

            session.beginTransaction();
            session.save(account);
            session.save(transaction);
            session.getTransaction().commit();
            session.close();


        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            CreateSessionFactory.sessionFactory.close();
        }

    }

}
