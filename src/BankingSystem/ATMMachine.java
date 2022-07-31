package BankingSystem;

import org.hibernate.HibernateException;

import javax.persistence.*;
import java.util.*;

@Entity
public class ATMMachine extends BankingManagementSystem {
    @ElementCollection
     // has how many denominations a particular note has
    private Map<Integer, Integer> cashVault = new HashMap<>();
    private boolean isActive; // whether its active or not
    @Embedded
    private Location atmLocation;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int atmId;

    private static Scanner sc = new Scanner(System.in);

    ATMMachine()
    {

    }

    public ATMMachine(Map<Integer, Integer> cashVault, boolean isActive, Location atmLocation) {
        this.cashVault = insertCashIntoCashVault();
        this.isActive = isActive;
        this.atmLocation = atmLocation;
    }
    // adds the cash into atm machine.
    private Map<Integer, Integer> insertCashIntoCashVault() {
        cashVault.put(1,99999);
        cashVault.put(5,99999);
        cashVault.put(10,99999);
        cashVault.put(20,99999);
        cashVault.put(50,99999);
        cashVault.put(100,99999);
        cashVault.put(200,99999);
        cashVault.put(500,99999);
        cashVault.put(1000,99999);
        return cashVault;
    }

    public Map<Integer, Integer> getCashVault() {
        return cashVault;
    }

    public void setCashVault(Map<Integer, Integer> cashVault) {
        this.cashVault = cashVault;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Location getAtmLocation() {
        return atmLocation;
    }

    public void setAtmLocation(Location atmLocation) {
        this.atmLocation = atmLocation;
    }

    public int getAtmId() {
        return atmId;
    }

    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }

    private boolean authenticateCard(ATMCard atmCard){
        Account user = atmCard.getCardHolderAccount();
        boolean userExists = Util.authenticateUser(user.getName(),user.getAccountNo());

        if(!userExists)
            return true;

        boolean isBannedOrExpired = Util.checkBannedOrExpired(atmCard);
        if(isBannedOrExpired)
            return true;


        short attempts = 3; // you get a max of 3 attempts before your card gets banned for a day.
        while (true)
        {
            System.out.println("Please enter your current MPIN.");
            short mpin = sc.nextShort();

            if(mpin == atmCard.getMPin())
                break;
            else {
                attempts--;
                if(attempts > 0)
                    System.out.println("Your pin is incorrect. You have " + attempts + " remaining");
                else
                {
                    // card gets banned for a day after 3 unsuccessful attempts.
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH);
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    atmCard.setBanDate(new GregorianCalendar(year,month,day+1));
                    System.out.println("You have exceeded your 3 attempts. You are banned for a day.");
                    return true;
                }
            }

        }
        return false;
    }
    public void changePin(ATMCard atmCard){

        if(authenticateCard(atmCard))
            return;


        System.out.println("Please enter a 4 digit your new pin.");
        short newPin = sc.nextShort();
        System.out.println("Please confirm your 4 digit new pin.");
        short confirmPin = sc.nextShort();
        // confirming length
        if(Short.toString(newPin).length() != 4 || Short.toString(confirmPin).length() != 4){
            System.out.println("Your pin should be of 4 digit length");
            return;
        }

        if(newPin == confirmPin)
        {
            atmCard.setMPin(newPin);
            System.out.println("Your pin has been updated.");

        }
        else
        {
            System.out.println("New pin and confirm pin must be same. ");
        }

        Transaction transaction =  new Transaction(Constants.CHANGE_PIN,new GregorianCalendar(),atmCard.getCardHolderAccount());
        atmCard.getCardHolderAccount().getTransactionHistory().add(transaction);
        System.out.println(transaction.getTransactionDescription());

    }


    public void transferFunds(ATMCard atmCard){

        if(authenticateCard(atmCard))
            return;

        transferFunds(atmCard.getCardHolderName(),atmCard.getCardHolderAccount().getAccountNo());
    }


    public void transactionHistory(ATMCard atmCard){

        if(authenticateCard(atmCard))
            return;

        transactionHistory(atmCard.getCardHolderName(),atmCard.getCardHolderAccount().getAccountNo());
    }


    public void updateAccountDetails(ATMCard atmCard){
        if(authenticateCard(atmCard))
            return;

        updateAccount(atmCard.getCardHolderName(),atmCard.getCardHolderAccount().getAccountNo());
    }


    public void withdraw(ATMCard atmCard){

        if(authenticateCard(atmCard))
            return;

        System.out.println("Please enter the amount you want to withdraw!");
        double amount = Math.floor(sc.nextDouble()); // because the machine cannot dispense cash for the fractional part

        try{

            Account userAccount = atmCard.getCardHolderAccount();
            if(userAccount.getBalance() >= amount){
                if(userAccount.getBalance() >= amount) {
                    giveMoneyUsingCashVault(amount); // prints the money using coin exchange under limited cash denominations

                    userAccount.setBalance(userAccount.getBalance() - amount);
                    Transaction transaction = new Transaction(Constants.WITHDRAW,userAccount,new GregorianCalendar(),amount);
                    userAccount.getTransactionHistory().add(transaction);
                    System.out.println(transaction.getTransactionDescription());

                }
                else{
                    System.out.println("You don't have enough balance.");
                }


            }
        }catch (HibernateException e){
            e.printStackTrace();
            CreateSessionFactory.sessionFactory.close();
        }


    }

    private void giveMoneyUsingCashVault(double amount) {
        assert amount < Integer.MAX_VALUE;

        int[] denominations = {1,2,5,10,20,50,100,500,1000};
        int[][] dpMatrix = new int[(int)amount + 1][denominations.length + 1];

    }

    public void payLoanEmi(ATMCard atmCard){

        if(authenticateCard(atmCard))
            return;

        Account account = atmCard.getCardHolderAccount();
        // uses the loan management system here.
        LoanManagementSystem.payLoanEmi(account);

        }
}

