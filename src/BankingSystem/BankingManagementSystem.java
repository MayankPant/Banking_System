package BankingSystem;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.Date;
import java.util.Scanner;

public class BankingManagementSystem {

    static Scanner sc = new Scanner(System.in);

    public static void createAccount(String name, String phoneNo, String email, String nominee) {
        double balance = -1;
        // providing an option for zero balance account
        System.out.println("Do you want to create a zero balance account? Press 1 for Yes and an 2 for a No");
        short option = Util.twoOptionMenu("Yes","No");
        if(option == 1)
            balance = 0;
        else
            System.out.println("Please enter the starting balance.");
            balance = sc.nextDouble();

        Account account = new Account(name, phoneNo, email, nominee, balance);
        Transaction transaction = new Transaction(Constants.CREATE, account, new Date(), balance);
        account.getTransactionHistory().add(transaction);


        try {
            Session session = CreateSessionFactory.sessionFactory.openSession();

            session.beginTransaction();
            session.persist(account);
            session.getTransaction().commit();
            session.close();
            System.out.println("Your account has been created with account no " + account.getAccountNo());
            System.out.println(transaction.getTransactionDescription()); // always carries the last transaction

        } catch (Exception e) {
            System.out.println(e.getMessage());
            CreateSessionFactory.sessionFactory.close();
        }

    }

    public static void removeAccount(String name, long accountNo) {
        System.out.println("Are you sure you want to remove your account? Enter 1 for yes and 2 for no.");
        short option = Util.twoOptionMenu("Yes","No");
        if(option == 2)
        {
            System.out.println("Account removal request Aborted.");
            return;
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
            Transaction transaction = new Transaction(Constants.UPDATE,new Date(),account);
            account.getTransactionHistory().add(transaction);
            System.out.println(transaction.getTransactionDescription());
            session.saveOrUpdate(account);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            CreateSessionFactory.sessionFactory.close();
            return false;
        }

    }

    public static void transferFunds(String name, long accountNo)
    {
        boolean userExists = Util.authenticateUser(name, accountNo);

        if(!userExists)
            return;

        System.out.println("Enter the name of the receiving user.");
        String receiverName = sc.nextLine();
        System.out.println("Enter the account number of the receiving user.");
        long receiverAccountNo = sc.nextLong();

        userExists = Util.authenticateUser(receiverName,receiverAccountNo);

        if(!userExists)
            return;

        System.out.println("Enter the amount to be transferred.");
        double transferredFund = sc.nextDouble();

        // cash and from account options are exclusive to bank and wont be there in atm system
        System.out.println("Enter the medium for transfer");
        short option = Util.twoOptionMenu("through cash.","through account.");
        /*
        * At this point if the user selects option 1, the employee from the bank
        * will take the cash amount from him and the money will not be deducted
        * from his own account. If the user chooses option 2, the amount for transfer will
        * be deducted from his own account and added into the receivers account.
        * */


        try{
            Session session  = CreateSessionFactory.sessionFactory.openSession();
            Account user = session.get(Account.class, accountNo);
            Account receiver = session.get(Account.class, receiverAccountNo);

            if(option == 1)
            {
                receiver.setBalance(receiver.getBalance() + transferredFund);
            }
            else {
                if(user.getBalance() >= transferredFund) {
                    user.setBalance(user.getBalance() - transferredFund);
                    receiver.setBalance(receiver.getBalance() + transferredFund);
                }
                else {
                    System.out.println("You don't have enough balance in your account");
                    System.out.println("Do you want to transfer using cash instead?");
                    option = Util.twoOptionMenu("Yes","No");
                    if(option == 1)
                    {
                        // employee now takes cash from him.
                        receiver.setBalance(receiver.getBalance() + transferredFund);
                    }
                    else
                        return;
                }
                session.beginTransaction();
                Transaction transactionUser = new Transaction(Constants.TRANSFER,user,new Date(),transferredFund,user);
                Transaction transactionReceiver = new Transaction(Constants.RECEIVE,receiver,new Date(),transferredFund, receiver);
                user.getTransactionHistory().add(transactionUser);
                receiver.getTransactionHistory().add(transactionReceiver);
                System.out.println(transactionUser.getTransactionDescription()); //prints a log here

                session.update(user);
                session.update(receiver);
                session.getTransaction().commit();
                session.close();

            }
        }catch (HibernateException e)
        {
            e.printStackTrace();
            CreateSessionFactory.sessionFactory.close();
        }
    }

    public static void withdrawOrDeposit(String name, long accountNo)
    {
        boolean userExists = Util.authenticateUser(name, accountNo);

        if(!userExists)
            return;

        System.out.println("Please enter the amount you want to withdraw or deposit.");
        double amount = sc.nextDouble();

        short option = Util.twoOptionMenu("Withdraw","Deposit");

        try {
            Session session = CreateSessionFactory.sessionFactory.openSession();
            session.beginTransaction();
            Account account = session.get(Account.class, accountNo);

            if(option == 1)
            {
                if(account.getBalance() >= amount) {
                    amount = Math.floor(amount); // to round off for the algorithm of coin exchange
                    Util.giveMoney(amount); // prints the money using coin exchange

                    account.setBalance(account.getBalance() - amount);
                    Transaction transaction = new Transaction(Constants.WITHDRAW,account,new Date(),amount);
                    account.getTransactionHistory().add(transaction);
                    System.out.println(transaction.getTransactionDescription());

                }
                else{
                    System.out.println("You don't have enough balance.");
                    return;
                }
            }
            else if(option == 2)
            {
                account.setBalance(account.getBalance() + amount);
                Transaction transaction = new Transaction(Constants.DEPOSIT,account,new Date(),amount);
                account.getTransactionHistory().add(transaction);
                System.out.println(transaction.getTransactionDescription());
            }
            session.saveOrUpdate(account);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            CreateSessionFactory.sessionFactory.close();
        }
    }
}
