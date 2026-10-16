# ICS 4U0 — Lesson 14: Inheritance

## Exercise

The bank from Lesson 12 is adding chequing accounts. A chequing account is a `BankAccount` that charges a **fee on every withdrawal**. The teller program is already written, in `src/BankTeller.java`. It opens a chequing account, tries each withdrawal, and prints the account. **It only works once your `ChequingAccount` class does.**

`BankAccount` from Lesson 12 is complete in `src/BankAccount.java`. **Don't change it.** Write `src/ChequingAccount.java` to match this UML diagram:

```
┌─────────────────────────────────────────┐
│ BankAccount                             │
├─────────────────────────────────────────┤
│ - owner : String                        │
│ - balance : double                      │
├─────────────────────────────────────────┤
│ + BankAccount(owner : String)           │
│ + BankAccount(owner : String,           │
│               balance : double)         │
│ + getOwner() : String                   │
│ + getBalance() : double                 │
│ + deposit(amount : double) : boolean    │
│ + withdraw(amount : double) : boolean   │
│ + toString() : String                   │
└─────────────────────────────────────────┘
                    △
                    │
┌───────────────────┴─────────────────────┐
│ ChequingAccount                         │
├─────────────────────────────────────────┤
│ - fee : double                          │
├─────────────────────────────────────────┤
│ + ChequingAccount(owner : String,       │
│                   balance : double,     │
│                   fee : double)         │
│ + getFee() : double                     │
│ + withdraw(amount : double) : boolean   │
│ + toString() : String                   │
└─────────────────────────────────────────┘
```

(The diagram leaves out `BankAccount`'s private helper, its overloaded `deposit`, and its `static` counter. You don't need them.)

Everything you need is from Lesson 14:

- **`extends`** and a constructor that calls **`super(...)`** first,
- a **new attribute** and **getter**, and
- two **overrides** that use **`super.`** to reuse `BankAccount`'s versions.

| Method | What it does |
|---|---|
| `ChequingAccount(owner, balance, fee)` | Passes `owner` and `balance` up to `BankAccount`, then sets the fee. |
| `getFee()` | Returns the fee. |
| `withdraw(amount)` | Takes out `amount` **plus** the fee, as one withdrawal. If `amount` is 0 or less, returns `false` and charges nothing. If `amount` plus the fee is more than the balance, returns `false` and changes nothing. Otherwise returns `true`. |
| `toString()` | Returns `BankAccount`'s text with ` (chequing)` added to the end, for example `Grace: $148.50 (chequing)`. |

- `ChequingAccount.java` already has every header, and a placeholder `super("", 0);` so the project compiles from the start. Replace each placeholder line.
- `balance` is private in `BankAccount`, so `ChequingAccount` can't use it by name. Use `super.withdraw(...)` and `super.toString()`.
- **Don't change `BankTeller.java`.**

---

## Input

Four lines, then one line per withdrawal (`BankTeller` reads them for you):

| Line | Value | Type |
|------|-------|------|
| 1 | Owner's name | text |
| 2 | Starting balance | decimal number |
| 3 | Fee per withdrawal | decimal number |
| 4 | Number of withdrawals, `n` | whole number |
| next `n` | One withdrawal amount per line | decimal number |

Example input:

```
Grace
250.00
1.50
2
100.00
500.00
```

---

## Output

```
Withdraw $<amount>: approved          (or declined, one line per withdrawal)
Fee per withdrawal: $<fee>
<owner>: $<balance> (chequing)
```

For the example input above, the program must print **exactly**:

```
Withdraw $100.00: approved
Withdraw $500.00: declined
Fee per withdrawal: $1.50
Grace: $148.50 (chequing)
```

`BankTeller` prints these lines, so every difference comes from your `ChequingAccount` class.

---

## Testing

- Test your code yourself first.
- Open the **Testing** panel from the sidebar (flask icon) and click ▶ **Run Tests**.

A green check next to a test means it passed; a red X means it failed and will show you the expected vs. actual output.
