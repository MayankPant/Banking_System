package BankingSystem;


public class UtilLog {

    protected static String log(Enum<Constants> event) {
        // creation at zero balance
        if (event.equals(Constants.CREATE)) {
            return logCreateAccount();
        } else if (event.equals(Constants.UPDATE)) {
            return logUpdateAccount();
        } else if (event.equals(Constants.REMOVE)) {
            return logRemoveAccount();
        } else if (event.equals(Constants.QUERY)) {
            return logQuery();
        } else
            return null;
    }

    private static String logQuery() {

        return "A query was generated about your account";
    }

    private static String logRemoveAccount() {
        return "Your account was removed from the system.";
    }

    private static String logUpdateAccount() {
        return "The details for your account were updated";
    }

    private static String logCreateAccount() {
        return "Your account was created with zero balance.";
    }

    // overloaded method for other logs.
    protected static String log(Enum<Constants> event, long amount) {

        if (event.equals(Constants.DEPOSIT)) {
            return logDeposit(amount);
        } else if (event.equals(Constants.WITHDRAW)) {
            return logWithdraw(amount);
        } else if (event.equals(Constants.INTEREST)) {
            return logInterest(amount);
        } else
            return null;

    }

    private static String logInterest(long amount) {
        return "An Interest of " + amount + " was debited to your account";
    }

    private static String logWithdraw(long amount) {
        return "An amount of " + amount + " was withdrawn from your account";
    }

    private static String logDeposit(long amount) {
        return "An amount of " + amount + " was deposited to your account";
    }
// generalised log for very different kinds of transaction. Further logs can be added here
    protected static String log(Enum<Constants> event, Object transactionDetail, long amount) {

        if(event.equals(Constants.TRANSFER))
        {
            Account transferAccount = (Account) transactionDetail;
            return "You transferred an amount of "+amount+" to "+transferAccount.getName()+" with an account number "+
                    transferAccount.getAccountNo();
        }

        else {
            return null;
        }


    }
}

