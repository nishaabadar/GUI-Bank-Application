package coe528.project;

/**
 * Overview: The PlatinumState class represents the state of a customer with a platinum level in the banking system.
 */
public class PlatinumState implements State {

    /**
     * Processes a purchase transaction for a customer in the platinum state, without applying any transaction fee.
     */
    @Override
    public void purchase(Customer customer, double amount) throws Exception {
        if (amount < 50) {
            throw new Exception("Minimum purchase amount is $50");
        }
        if (customer.getBalance() < amount) {
            throw new Exception("Insufficient funds for purchase.");
        }
        customer.withdraw(amount); // Withdraw the purchase amount only, no fee
    }
}