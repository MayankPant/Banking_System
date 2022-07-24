package BankingSystem;

import org.hibernate.Session;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

// has utility methods for the application
public class Util {

    // current currency at the bank
    /*
    * here arr[i] = value of the denomination at the ith index.
    * we currently assume that bank has an unlimited supply of denominations
    * this assumption might be changed when creating an atm machine.
    * */

    static int[] denominations = {1,2,5,10,20,50,100,200,500,1000}; //currency notes at the bank

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

    protected static void giveMoney(double amount) {
        // the algorithm will discard the fractional part btw
        int[][] dpMatrix = new int[(int)amount+1][denominations.length+1];
        coinExchange((int)amount, dpMatrix);
    }

    private static void coinExchange(int amount, int[][] dpMatrix) {

        // base cases
        for(int i = 0; i <= amount; i++)
            dpMatrix[i][0] = i;

        for(int j = 0; j <= denominations.length; j++)
            dpMatrix[0][j] = 0;

        //filling the table

        for(int i = 1; i <= amount; i++)
        {
            for (int j = 1; j <= denominations.length; j++)
            {
                if(i >= denominations[j-1])
                {
                    // picked and not picked
                    dpMatrix[i][j] = Math.min((1+dpMatrix[i - denominations[j-1]][j]),(dpMatrix[i][j-1]));
                }
                else {
                    dpMatrix[i][j] = dpMatrix[i][j - 1];
                }
            }
        }

        // backtracking to get the chosen coins
        System.out.println("Here is your cash. Have a great day!");
        int i = amount;
        int j = denominations.length;
        while (i > 0 && j > 0)
        {
            if(dpMatrix[i][j] != dpMatrix[i][j-1])
            {
                System.out.print(denominations[j-1]+" ");
                i = i - denominations[j-1];
            }
            else
                j = j-1;
        }
        System.out.println();
    }

    public static boolean checkBannedOrExpired(ATMCard atmCard) {

        Calendar curDate = new GregorianCalendar();
        if(curDate.after(atmCard.getBanDate()) && curDate.before(atmCard.getExpiryDate()))
            return false;
        else
        {
            if(curDate.after(atmCard.getExpiryDate()))
                System.out.println("Your card is expired. Please send a request for a new one.");
            else if(curDate.before(atmCard.getBanDate()))
                System.out.println("Your card is banned.");

            return true;
        }
    }

}
