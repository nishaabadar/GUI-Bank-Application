package coe528.project;

/**
 * Overview: The Customer class represents a bank customer with an account and a customer level that
 * changes based on the account balance. It allows depositing, withdrawing, and making purchases.
 * This class is mutable as the account balance and customer level can change.
 *
 * Abstraction Function: Represents a customer with an account balance and a customer level.
 *
 * Rep Invariant: account must not be null, and customerLevel must be one of SilverState, GoldState, or PlatinumState.
 */
public class Customer extends User {
    private Account account;
    private State customerLevel;

    /**
     * Constructs a Customer with a username, password, and initializes the account and customer level.
     *
     * @param username the username of the customer
     * @param password the password of the customer
     * @requires username != null && password != null
     * @modifies this.account, this.customerLevel
     * @effects initializes the customer with an account and sets the initial customer level based on the account balance
     */
    public Customer(String username, String password) {
        super(username, password, "customer");
        this.account = new Account(100); // Initial balance of 100
        updateCustomerLevel(); // Set initial customer level
    }

    /**
     * Deposits the specified amount into the customer's account and updates the customer level.
     *
     * @param amount the amount to deposit
     * @requires amount > 0
     * @modifies this.account, this.customerLevel
     * @effects deposits the amount into the account and updates the customer level
     */
    public void deposit(double amount) {
        account.deposit(amount);
        updateCustomerLevel(); // Update level after deposit
    }

    /**
     * Withdraws the specified amount from the customer's account and updates the customer level.
     *
     * @param amount the amount to withdraw
     * @requires amount > 0 && amount <= getBalance()
     * @modifies this.account, this.customerLevel
     * @effects withdraws the amount from the account and updates the customer level
     */
    public void withdraw(double amount) {
        account.withdraw(amount);
        updateCustomerLevel(); // Update level after withdrawal
    }

    /**
     * Returns the current balance of the customer's account.
     *
     * @return the current balance of the account
     * @effects returns the current balance of the customer's account
     */
    public double getBalance() {
        return account.getBalance();
    }

    /**
     * Processes a purchase of the specified amount and updates the customer level.
     *
     * @param amount the amount of the purchase
     * @throws Exception if the purchase cannot be completed
     * @requires amount > 0
     * @modifies this.account, this.customerLevel
     * @effects processes the purchase and updates the customer level
     */
    public void makePurchase(double amount) throws Exception {
        this.customerLevel.purchase(this, amount);
        updateCustomerLevel(); // Update the customer level in case the balance change affects the level
    }

    /**
     * Updates the customer's level based on the account balance.
     *
     * @modifies this.customerLevel
     * @effects updates the customer level based on the current account balance
     */
    private void updateCustomerLevel() {
        double balance = this.account.getBalance();
        if (balance < 10000) {
            this.customerLevel = new SilverState();
        } else if (balance >= 10000 && balance < 20000) {
            this.customerLevel = new GoldState();
        } else if (balance >= 20000) {
            this.customerLevel = new PlatinumState();
        }
    }

    /**
     * Returns the current state of the customer.
     *
     * @return the current state of the customer
     * @effects returns the current customer level
     */
    public State getState() {
        return this.customerLevel;
    }

    /**
     * Returns a string representation of the customer.
     *
     * @return a string representation of the customer
     * @effects returns a string that represents the customer
     */
    @Override
    public String toString() {
        return "Customer{username=" + getUsername() + ", balance=" + getBalance() + ", level=" + customerLevel.getClass().getSimpleName() + "}";
    }

    /**
     * Checks the representation invariant of the customer.
     *
     * @return true if the rep invariant holds for this object, false otherwise
     */
    public boolean repOk() {
        return account != null && (customerLevel instanceof SilverState || customerLevel instanceof GoldState || customerLevel instanceof PlatinumState);
    }
}