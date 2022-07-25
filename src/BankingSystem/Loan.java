package BankingSystem;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "Loans")
public class Loan {
    private double loanAmount;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;
    @OneToOne
    @JoinColumn(name = "Account_No")
    private Account account;

    private long emi;

    private Calendar loanTakenOn;
    private int doneEMIs;
    private int remainingEMIs;

    @OneToOne
    @JoinColumn(name = "card_no")
    private ATMCard atmCard;

    // default constructor for hibernate
    public Loan()
    {

    }

    public Loan(double loanAmount, Account account, long emi, Calendar loanTakenOn, int doneEMIs, int remainingEMIs) {
        this.loanAmount = loanAmount;
        this.account = account;
        this.emi = emi;
        this.loanTakenOn = loanTakenOn;
        this.doneEMIs = doneEMIs;
        this.remainingEMIs = remainingEMIs;
    }

    public ATMCard getAtmCard() {
        return atmCard;
    }

    public void setAtmCard(ATMCard atmCard) {
        this.atmCard = atmCard;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getEmi() {
        return emi;
    }
    // this method should be inaccessible directly so its private
    private void setEmi(long emi) {
        this.emi = emi;
    }

    public Calendar getLoanTakenOn() {
        return loanTakenOn;
    }

    public void setLoanTakenOn(Calendar loanTakenOn) {
        this.loanTakenOn = loanTakenOn;
    }

    public int getDoneEMIs() {
        return doneEMIs;
    }

    public void setDoneEMIs(int doneEMIs) {
        this.doneEMIs = doneEMIs;
    }

    public int getRemainingEMIs() {
        return remainingEMIs;
    }

    public void setRemainingEMIs(int remainingEMIs) {
        this.remainingEMIs = remainingEMIs;
    }
}
