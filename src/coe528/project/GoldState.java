package coe528.project;

/**
 * Overview: The GoldState class represents the state of a customer with a gold level in the banking system.
 */
public class GoldState implements State {

    /**
     * Processes a purchase transaction for a customer in the gold state, applying the necessary fee.
     */
    @Override
    public void purchase(Customer customer, double amount) throws Exception {
        final double fee = 10.00; // Reduced fee for gold state
        if (amount < 50) {
            throw new Exception("Minimum purchase amount is $50");
        }
        if (customer.getBalance() < amount + fee) {
            throw new Exception("Insufficient funds for purchase and fee.");
        }
        customer.withdraw(amount + fee); // Withdraw the purchase amount plus fee
    }
}