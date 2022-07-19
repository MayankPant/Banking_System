package BankingSystem;

import org.hibernate.Session;

// has utility methods for the application
public class Util {

    public static boolean authenticateUser(String name, long accountNo)
    {
        Session session = CreateSessionFactory.sessionFactory.openSession();

        session.beginTransaction();
        Account account = session.get(Account.class, accountNo);

        if(account == null) {
            System.out.println("Your account does not exist.");
            return false;
        }
        else if(!account.getName().equals(name)) {
            System.out.println("Your name is wrong.");
            return false;
        }
        else {
            return true;
        }

    }

}
