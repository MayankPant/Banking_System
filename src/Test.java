import BankingSystem.*;
import BankingSystem.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        Account account = new Account("Mayank","1234","mpant","shashank",500);
        Transaction transaction = new Transaction(Constants.CREATE,new Date());
        account.getTransactionHistory().add(transaction);

        session.beginTransaction();
        session.save(account);
        session.save(transaction);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}