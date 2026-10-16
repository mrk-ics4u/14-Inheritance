/*
 * ChequingAccountTest.java -- unit tests for the Lesson 14 exercise
 * ICS 4U0 - Lesson 14 Exercise: ChequingAccount
 *
 * These tests run BankTeller.main() exactly as it will be graded: they type
 * the input lines on System.in and check the lines it prints to System.out.
 * BankTeller only works if your ChequingAccount class does, so every failure
 * here points at ChequingAccount.java. You don't need to change this file --
 * just run the tests (see README.md) and fix ChequingAccount.java until they
 * all pass.
 */
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChequingAccountTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private Locale originalLocale;

    @BeforeEach
    public void redirectIoAndPinLocale() {
        originalIn = System.in;
        originalOut = System.out;
        originalLocale = Locale.getDefault();
        // printf follows the default locale -- pin it so "%.2f" prints
        // 148.50, not 148,50, on every machine.
        Locale.setDefault(Locale.CANADA);
    }

    @AfterEach
    public void restoreIoAndLocale() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        Locale.setDefault(originalLocale);
    }

    /**
     * Feeds {@code input} to BankTeller.main() on System.in and returns
     * everything it printed to System.out.
     */
    private String run(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));

        BankTeller.main(new String[0]);

        return captured.toString(StandardCharsets.UTF_8);
    }

    /**
     * Compares output line by line, trimming trailing whitespace on each
     * line and ignoring one blank line at the very end (println's final
     * newline). Everything else -- including every space, colon, and quote -- is
     * compared exactly.
     */
    private void assertOutputEquals(String expected, String actual) {
        String[] expectedLines = expected.stripTrailing().split("\n", -1);
        String[] actualLines = actual.stripTrailing().split("\n", -1);

        assertEquals(expectedLines.length, actualLines.length,
                "Expected " + expectedLines.length + " line(s) of output, got " + actualLines.length
                        + ".\n--- expected ---\n" + expected + "--- actual ---\n" + actual);

        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i].stripTrailing(), actualLines[i].stripTrailing(),
                    "Line " + (i + 1) + " didn't match.\n--- expected ---\n" + expected
                            + "--- actual ---\n" + actual);
        }
    }

    @Test
    public void exampleFromReadme() {
        String input = "Grace\n250.00\n1.50\n2\n100.00\n500.00\n";
        String expected = "Withdraw $100.00: approved\n"
                + "Withdraw $500.00: declined\n"
                + "Fee per withdrawal: $1.50\n"
                + "Grace: $148.50 (chequing)\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void constructorPassesOwnerAndBalanceToBankAccount() {
        // No withdrawals, so the balance must be exactly what the constructor got.
        // super(owner, balance) has to pass BOTH values up, and the fee needs this.fee = fee;
        String input = "Alan\n80.00\n2.00\n0\n";
        String expected = "Fee per withdrawal: $2.00\n"
                + "Alan: $80.00 (chequing)\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void withdrawalChargesTheFee() {
        // Each approved withdrawal takes the amount AND the fee. Without an override
        // (or with super.withdraw(amount)), the fee is never charged.
        String input = "Ada\n500.00\n2.50\n3\n100.00\n50.00\n20.00\n";
        String expected = "Withdraw $100.00: approved\n"
                + "Withdraw $50.00: approved\n"
                + "Withdraw $20.00: approved\n"
                + "Fee per withdrawal: $2.50\n"
                + "Ada: $322.50 (chequing)\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void feeCountsTowardTheLimit() {
        // 99.00 + 1.50 fee is more than 100.00, so it's declined and nothing changes.
        // Withdrawing the amount and then the fee separately would leave 1.00.
        String input = "Linus\n100.00\n1.50\n1\n99.00\n";
        String expected = "Withdraw $99.00: declined\n"
                + "Fee per withdrawal: $1.50\n"
                + "Linus: $100.00 (chequing)\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void exactAmountPlusFeeIsApproved() {
        // 100.00 + 1.50 fee is exactly the balance, so it's approved and leaves 0.00.
        String input = "Grace\n101.50\n1.50\n1\n100.00\n";
        String expected = "Withdraw $100.00: approved\n"
                + "Fee per withdrawal: $1.50\n"
                + "Grace: $0.00 (chequing)\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void nonPositiveWithdrawalIsDeclinedWithNoFee() {
        // A withdrawal of 0 or less is declined and charges no fee. super.withdraw(amount + fee)
        // alone would approve -1.00 + 1.50 = 0.50. Check amount first.
        String input = "Ada\n60.00\n1.50\n2\n-1.00\n0.00\n";
        String expected = "Withdraw $-1.00: declined\n"
                + "Withdraw $0.00: declined\n"
                + "Fee per withdrawal: $1.50\n"
                + "Ada: $60.00 (chequing)\n";
        assertOutputEquals(expected, run(input));
    }

    @Test
    public void toStringExtendsBankAccountVersion() {
        // toString returns BankAccount's text plus " (chequing)". Calling toString()
        // without super. calls itself forever. Use super.toString().
        String input = "Margaret\n1234.56\n0.75\n1\n34.56\n";
        String expected = "Withdraw $34.56: approved\n"
                + "Fee per withdrawal: $0.75\n"
                + "Margaret: $1199.25 (chequing)\n";
        assertOutputEquals(expected, run(input));
    }
}
