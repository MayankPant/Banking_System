import BankingSystem.*;
import org.hibernate.Session;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        BankingManagementSystem.createAccount("Amit","1234","apant","Shekhar");
       BankingManagementSystem.createAccount("Mayank","345","mpant","shashank");
//        BankingManagementSystem.removeAccount("Amit",2175171210146877L);
//        BankingManagementSystem.updateAccount("Amit Pant",5156203165516762L);

//        Session session = CreateSessionFactory.sessionFactory.openSession();
//
//        session.beginTransaction();
//        Account account = session.get(Account.class, 8684501864611986L);
//        Loan loan = new Loan(50000,null,400,new Date(),3,8);
//        account.setLoan(loan);
//        loan.setAccount(account);
//        session.update(account);
//        session.save(loan);
//        session.getTransaction().commit();
//        session.close();

//        BankingManagementSystem.transferFunds("Mayank",1863572361369511L);
        BankingManagementSystem.createAccount("Niharika","3456","n@gamil","Mayank");
 //       BankingManagementSystem.withdrawOrDeposit("Niharika",1304273999255917L);
        BankingManagementSystem.withdrawOrDeposit("Niharika",1304273999255917L);


    }
}