package BankingSystem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

    private String name;
    @Id
    private long accountNo;
    @Transient
    private final static byte noOfTransactions = 3;
    private String phoneNo;
    private String email;
    private String nominee;
    private double balance;
    @Embedded
    private ATMCard atmCard;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Loan loan;


    private Calendar lastInterestDate; // when was the interest given last Date
    @Transient
    private Calendar creationDate; // when was the account created

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Collection<Transaction> transactionHistory;

    // default constructor for hibernate as it uses a java bean
    public Account()
    {

    }
    public Account(String name, String phoneNo, String email, String nominee, double balance) {
        this.name = name;
        this.accountNo = createAccountNo();
        this.phoneNo = phoneNo;
        this.email = email;
        this.nominee = nominee;
        this.balance = balance;
        this.atmCard = null;
        this.loan = null;
        this.creationDate = new GregorianCalendar();
        this.lastInterestDate = null;
        this.transactionHistory = new ArrayList<Transaction>();
    }

    private long createAccountNo() {
        // generates the number again in case he gets a 15 digit value.
        long randomNo ;
        do{
            randomNo = (long)(Math.random() * 10000000000000000L);
        }while (Long.toString(randomNo).length() != 16);

        return randomNo;
    }

    public String getName() {
        return name;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public static byte getNoOfTransactions() {
        return noOfTransactions;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public String getNominee() {
        return nominee;
    }

    public double getBalance() {
        return balance;
    }

    public ATMCard getAtmCard() {
        return atmCard;
    }

    public Loan getLoan() {
        return loan;
    }

    public Calendar getLastInterestDate() {
        return lastInterestDate;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAtmCard(ATMCard atmCard) {
        this.atmCard = atmCard;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void setLastInterestDate(Calendar lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }

    public Collection<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(Collection<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}
