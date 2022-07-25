import BankingSystem.*;
import org.hibernate.Session;

import java.util.GregorianCalendar;
import java.util.HashMap;


public class Test {
    public static void main(String[] args) {
//        BankingManagementSystem.createAccount("Amit","1234","apant","Shekhar");
  //     BankingManagementSystem.createAccount("Mayank","345","mpant","shashank");
//        BankingManagementSystem.removeAccount("Amit",2175171210146877L);
//        BankingManagementSystem.updateAccount("Amit Pant",5156203165516762L);

       Session session = CreateSessionFactory.sessionFactory.openSession();

        session.beginTransaction();
        ATMCard card = session.get(ATMCard.class, 2492753658867922L);

        ATMMachine atmMachine = new ATMMachine(new HashMap<>(), true,new Location("adham","new jersey","usa",213445));

 //       atmMachine.changePin(card);
   //     atmMachine.transferFunds(card);
     //   atmMachine.updateAccountDetails(card);
        session.save(atmMachine);
        session.getTransaction().commit();
        session.close();

        //BankingManagementSystem.transferFunds("Niharika",5664901559012112L);
     //   BankingManagementSystem.createAccount("Niharika","3456","n@gamil","Mayank");
 //       BankingManagementSystem.withdrawOrDeposit("Niharika",1304273999255917L);
    //    BankingManagementSystem.withdrawOrDeposit("Niharika",1304273999255917L);
//    BankingManagementSystem.transactionHistory("Mayank",1660800161228528L);

    }
}