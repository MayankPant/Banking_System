package BankingSystem;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class Transaction {

    private Date transactionDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private Enum<Constants> transactionType;
    @Lob // large object in the form of a text;
    private String transactionDescription;
    // default method for hibernate
    public Transaction()
    {

    }
    // for transactions where no amount is involved like creating/ removing accounts
    public Transaction(Enum<Constants> transactionType, Date transactionDate) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.transactionDescription = UtilLog.log(transactionType);
    }
    // for transactions where amount is involved like withdraw or deposit.
    public Transaction(Enum<Constants> transactionType, Date transactionDate, long amount) {
        this.transactionDate = transactionDate;

        this.transactionType = transactionType;
        this.transactionDescription = UtilLog.log(transactionType, amount);
    }
    // for transactions which have other requirements
    public Transaction(Enum<Constants> transactionType, Date transactionDate, long amount,Object transactionDetail) {
        this.transactionDate = transactionDate;

        this.transactionType = transactionType;
        this.transactionDescription = UtilLog.log(transactionType, transactionDetail, amount);
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

    public Enum<Constants> getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Enum<Constants> transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
