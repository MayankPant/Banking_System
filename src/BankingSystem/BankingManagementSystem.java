package BankingSystem;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.Date;
import java.util.Scanner;

public class BankingManagementSystem {

    static Scanner sc = new Scanner(System.in);

    public static void createAccount(String name, String phoneNo, String email, String nominee) {
        long balance = -1;
        // providing an option for zero balance account
        System.out.println("Do you want to create a zero balance account? Press Y for yes and an N for a No");
        char option = sc.next().charAt(0);
        boolean menuControl = true;

        while (menuControl) {
            switch (option) {
                case 'Y':
                    balance = 0;
                    menuControl = false;
                    break;
                case 'N':
                    System.out.println("Enter the amount you want to deposit to open your account.");
                    menuControl = false;
                    balance = sc.nextLong();
                    break;
                default:
                    System.out.println("Please enter a valid option or type \"QUIT\" to exit.");
                    String quitOption = sc.next();
                    if (quitOption.equals("QUIT")) {
                        System.out.println("Thanks for working with us.");
                        menuControl = false;
                        break;
                    }
                    break;
            }
        }

        Account account = new Account(name, phoneNo, email, nominee, balance);
        Transaction transaction = new Transaction(Constants.CREATE, account, new Date(), balance);
        account.getTransactionHistory().add(transaction);
        System.out.println("Your account has been created with account no " + account.getAccountNo());
        System.out.println(transaction.getTransactionDescription()); // always carries the last transaction


        try {
            Session session = CreateSessionFactory.sessionFactory.openSession();

            session.beginTransaction();
            session.persist(account);
            session.getTransaction().commit();
            session.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());
            CreateSessionFactory.sessionFactory.close();
        }

    }

    public static void removeAccount(String name, long accountNo) {
        System.out.println("Are you sure you want to remove your account? Enter Y for yes and N for no.");
        char option = sc.next().charAt(0);
        boolean menuControl = true;

        while (menuControl) {
            switch (option) {
                case 'Y':
                    menuControl = false;
                    break;
                case 'N':
                    System.out.println("Account removal request aborted.");
                    return;
                default:
                    System.out.println("Please enter a valid option or type \"QUIT\" to exit.");
                    String quitOption = sc.next();
                    if (quitOption.equals("QUIT")) {
                        System.out.println("Thanks for working with us.");
                        menuControl = false;
                        break;
                    } else {
                        option = quitOption.charAt(0);
                        break;
                    }
            }

        }
        try {
            Session session = CreateSessionFactory.sessionFactory.openSession();

            Account account = session.get(Account.class, accountNo);
            // adding the log of deletion.
            if (account.getName().equals(name)) {
                Transaction transaction = new Transaction(Constants.REMOVE, new Date(), account);
                account.getTransactionHistory().add(transaction);

                System.out.println(transaction.getTransactionDescription());

                session.beginTransaction();
                session.remove(account);
                session.getTransaction().commit();
            } else {
                System.out.println("Your name or account no is wrong.");
            }

            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    public static void updateAccount(String name, long accountNo) {

        boolean userExists = Util.authenticateUser(name, accountNo); // authenticates the user.
        if(!userExists)
            return;


        System.out.println("Please select the details you want to update about your account.\nYou can" +
                "select multiple items by entering a space separated list of options you want to upgrade.");
        System.out.println("1 for a Name change.\n2 for a phone number change.\n3 for an email change.\n4 for a nominee." +
                "change\n");
        String userInput = sc.nextLine(); // enter a space separated list of the above options
        String[] chosenOptions = userInput.split(" ");
        boolean updated = false;
        // goes through each value once and applies the updates
        for (String option : chosenOptions) {
            switch (option) {
                case "1":
                    System.out.println("Enter your new name.");
                    String newName = sc.nextLine();
                    updated = updateAccountDetails(option, accountNo, newName);
                    if(updated) {
                        System.out.println("Your name has been updated");
                    }
                    else
                    {
                        System.out.println("There was a problem while updating.\nPlease try again.");
                    }
                    break;
                case "2":
                    System.out.println("Enter new phone No without spaces");
                    String newPhoneNo = sc.next();
                    updated = updateAccountDetails(option, accountNo, newPhoneNo);
                    if(updated) {
                        System.out.println("Your phoneNo has been updated");
                    }
                    else
                    {
                        System.out.println("There was a problem while updating.\nPlease try again.");
                    }
                    break;
                case "3":
                    System.out.println("Enter new email No without spaces");
                    String newEmailId = sc.next();
                    updated = updateAccountDetails(option, accountNo, newEmailId);
                    if(updated) {
                        System.out.println("Your emailId has been updated.");
                    }
                    else
                    {
                        System.out.println("There was a problem while updating.\nPlease try again.");
                    }
                    break;
                case "4":
                    System.out.println("Enter the name for your new nominee without spaces");
                    String newNominee = sc.nextLine();
                    updated = updateAccountDetails(option, accountNo, newNominee);
                    if(updated) {
                        System.out.println("Your nominee has been updated.");
                    }
                    else
                    {
                        System.out.println("There was a problem while updating.\nPlease try again.");
                    }
                    break;
                default:
                    System.out.println("\""+option+"\"" + " is not a valid option. Scanning the remaining input");
                    break;
            }

        }
    }

    private static boolean updateAccountDetails(String option, long accountNo, String newDetail) {

        try {
            Session session = CreateSessionFactory.sessionFactory.openSession();

            Account account = session.get(Account.class, accountNo);
            session.beginTransaction();

            switch (option) {
                case "1":
                    account.setName(newDetail);
                    break;
                case "2":
                    account.setPhoneNo(newDetail);
                    break;
                case "3":
                    account.setEmail(newDetail);
                    break;
                case "4":
                    account.setNominee(newDetail);
                    break;
            }
            session.update(account);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            CreateSessionFactory.sessionFactory.close();
            return false;
        }

    }
}
