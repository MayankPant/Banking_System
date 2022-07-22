import BankingSystem.*;
import org.hibernate.Session;

import java.util.GregorianCalendar;


public class Test {
    public static void main(String[] args) {
//        BankingManagementSystem.createAccount("Amit","1234","apant","Shekhar");
  //     BankingManagementSystem.createAccount("Mayank","345","mpant","shashank");
//        BankingManagementSystem.removeAccount("Amit",2175171210146877L);
//        BankingManagementSystem.updateAccount("Amit Pant",5156203165516762L);

       Session session = CreateSessionFactory.sessionFactory.openSession();

        session.beginTransaction();
        Account account = session.get(Account.class, 5664901559012112L);

        Loan loan = new Loan(45639,account,232,new GregorianCalendar(),3,9);
        account.setLoan(loan);
        loan.setAccount(account);
      ATMCard card = new ATMCard(account, account.getLoan(), account.getName());
      account.setAtmCard(card);
        session.save(account);
        session.getTransaction().commit();
        session.close();

        //BankingManagementSystem.transferFunds("Niharika",5664901559012112L);
     //   BankingManagementSystem.createAccount("Niharika","3456","n@gamil","Mayank");
 //       BankingManagementSystem.withdrawOrDeposit("Niharika",1304273999255917L);
    //    BankingManagementSystem.withdrawOrDeposit("Niharika",1304273999255917L);
//    BankingManagementSystem.transactionHistory("Mayank",1660800161228528L);

    }
}