/*
 * Name:        Lesson 14: Inheritance (BankTeller.java)
 * Description: The program that uses your ChequingAccount class. Don't
 *              change this file. It reads the input, opens a chequing
 *              account, tries each withdrawal, and prints the account.
 * Created by:  Mr Kowalczewski
 * Last edited: 2026-10-13
 */
import java.util.Scanner;

public class BankTeller {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String owner = input.nextLine().trim();
        double balance = Double.parseDouble(input.nextLine().trim());
        double fee = Double.parseDouble(input.nextLine().trim());
        int count = Integer.parseInt(input.nextLine().trim());

        ChequingAccount account = new ChequingAccount(owner, balance, fee);

        for (int i = 0; i < count; i++) {
            double amount = Double.parseDouble(input.nextLine().trim());
            if (account.withdraw(amount)) {
                System.out.printf("Withdraw $%.2f: approved%n", amount);
            } else {
                System.out.printf("Withdraw $%.2f: declined%n", amount);
            }
        }

        System.out.printf("Fee per withdrawal: $%.2f%n", account.getFee());
        System.out.println(account);
    }
}
