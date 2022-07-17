package BankingSystem;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateSessionFactory {

    public static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); // keep this protected after testing
}
