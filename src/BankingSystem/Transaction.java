package BankingSystem;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    private Date transactionDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private String transactionType;
    @Lob // large object in the form of a text;
    private String transactionDescription;

    // for bidirectional mapping
    @ManyToOne
    @JoinColumn(name = "Account_No")
    private Account account;
    // default method for hibernate
    public Transaction()
    {

    }
    // for transactions where no amount is involved like creating/ removing accounts
    public Transaction(Enum<Constants> transactionType, Date transactionDate, Account account) {
        this.account = account;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType.toString();
        this.transactionDescription = UtilLog.log(transactionType);
    }
    // for transactions where amount is involved like withdraw or deposit.
    public Transaction(Enum<Constants> transactionType, Account account, Date transactionDate, long amount) {
        this.transactionDate = transactionDate;
        this.account = account;
        this.transactionType = transactionType.toString();
        this.transactionDescription = UtilLog.log(transactionType, amount);
    }
    // for transactions which have other requirements
    public Transaction(Enum<Constants> transactionType, Account account,  Date transactionDate, long amount,Object transactionDetail) {
        this.transactionDate = transactionDate;
        this.account = account;
        this.transactionType = transactionType.toString();
        this.transactionDescription = UtilLog.log(transactionType, transactionDetail, amount);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType.toString();
    }

    public void setTransactionType(Enum<Constants> transactionType) {
        this.transactionType = transactionType.toString();
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
