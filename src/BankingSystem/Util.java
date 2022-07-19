package BankingSystem;

import org.hibernate.Session;

import java.util.Scanner;

// has utility methods for the application
public class Util {

    protected static boolean authenticateUser(String name, long accountNo)
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
    /*
    * This method is used to create a simple two option menu.
    * This was created to avoid code repetition because a two
    * option menu is used quite often. Especially a "yes" or a "no"
    * one.
    *
    * returns 1 on choosing parameter 1 , 2 on choosing parameter 2.
    * */
    protected static short twoOptionMenu(String option1, String option2)
    {
        System.out.println("1. for "+option1);
        System.out.println("2. for "+option2);
        Scanner sc = new Scanner(System.in);
        String userOption = sc.next();

        if(userOption.equals("1"))
            return (short)1;
        else if(userOption.equals("2"))
            return (short)2;
        else
        {
            System.out.println("Please enter a valid input.");
            return twoOptionMenu(option1,option2);
        }

    }

}
