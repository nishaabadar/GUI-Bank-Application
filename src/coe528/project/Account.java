package coe528.project;

/**
 * Overview: Represents a bank account with basic functionalities like deposit and withdrawal.
 */
public class Account {
    private double balance;

    /**
     * Constructs an Account with the specified initial balance.
     */
    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Deposits the specified amount into the account.
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    /**
     * Withdraws the specified amount from the account.
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    /**
     * Returns the current balance of the account.
     */
    public double getBalance() {
        return balance;
    }
}