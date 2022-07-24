package BankingSystem;

import javax.persistence.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
public class ATMCard {
    @Id
    private  long cardNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Account_No")
    private  Account cardHolderAccount; // mapping

    @OneToOne(mappedBy = "atmCard", cascade = CascadeType.ALL)
    private Loan loan; // to pay emi's from atmMachines

    private  Calendar expiryDate;
    private Calendar banDate;
    private  short cvv;
    private short mPin;
    private  String cardHolderName;

    public ATMCard()
    {

    }


    public ATMCard(Account cardHolderAccount, Loan loan, String cardHolderName) {
        this.cardHolderAccount = cardHolderAccount;
        this.loan = loan;
        this.cardHolderName = cardHolderName;
        this.cardNumber = createCardNumber();
        this.mPin = createMpin();
        this.cvv = createCvv();
        this.banDate = new GregorianCalendar(); // gets the time during the creation because the checkBanned does not support null.
        Calendar creationDate = new GregorianCalendar();
        int year = creationDate.get(Calendar.YEAR);
        // works for 4 year.
        this.expiryDate = new GregorianCalendar(year + 4,creationDate.get(Calendar.MONTH),creationDate.get(Calendar.DAY_OF_MONTH));

    }


    private short createMpin() {

        short mPin;

        do{
            mPin = (short) (Math.random()*10000);
        }while (Short.toString(mPin).length() != 4);

        return mPin;

    }

    private long createCardNumber() {
        long cardNo;

        do{
            cardNo = (long)(Math.random() * 1000000000000L);
        }while (Long.toString(cardNo).length() != 12);

        return 2492 *  1000000000000L + cardNo ; // every  card no has the same first 4 characters to identify the bank
    }

    private short createCvv(){
        short cvv;
        do{
            cvv = (short) (Math.random() * 1000);
        }while (Short.toString(cvv).length() != 3);

        return cvv;
    }


    public long getCardNumber() {
        return cardNumber;
    }


    public Account getCardHolderAccount() {
        return cardHolderAccount;
    }

    public Calendar getBanDate() {
        return banDate;
    }

    public void setBanDate(Calendar banDate) {
        this.banDate = banDate;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Calendar getExpiryDate() {
        return expiryDate;
    }


    public short getCvv() {
        return cvv;
    }

    public short getMPin() {
        return mPin;
    }

    public void setMPin(short mPin) {
        this.mPin = mPin;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setmPin(short mPin) {
        this.mPin = mPin;
    }
    /*
    * The fields below are private because hibernate needs both setters and getters to create
    * proxy classes. But since these fields are not supposed to be changed, creating a setter
    * for them is not something you would want. Thus we used private getter and setter, which
    * makes sure that these arent accessed by an outside program, but hibernate can access these
    * using reflection methods.
    * */
    private void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    private void setCardHolderAccount(Account cardHolderAccount) {
        this.cardHolderAccount = cardHolderAccount;
    }

    private void setExpiryDate(Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    private void setCvv(short cvv) {
        this.cvv = cvv;
    }

    private void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
