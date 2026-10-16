/*
 * Name:
 * Description:
 * Created by:
 * Last edited:
 */
public class ChequingAccount extends BankAccount {

    // private attribute (see the UML diagram in README.md)


    // constructor: pass owner and balance up to BankAccount, then set the fee
    public ChequingAccount(String owner, double balance, double fee) {
        super("", 0);    // replace this line
    }

    // getter
    public double getFee() {
        return 0;        // replace this line
    }

    // withdraws amount plus the fee, using BankAccount's withdraw
    @Override
    public boolean withdraw(double amount) {
        return false;    // replace this line
    }

    // BankAccount's text with " (chequing)" added to the end
    @Override
    public String toString() {
        return "";       // replace this line
    }
}
