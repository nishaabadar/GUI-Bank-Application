package coe528.project;

/**
 * Overview: The Manager class represents a manager in the bank system with capabilities to add and delete customers.
 */
public class Manager extends User {
    /**
     * Constructs a Manager with the specified username and password.
     */
    public Manager(String username, String password) {
        super(username, password, "manager");
    }

    /**
     * Adds a new customer to the system.
     */
    public void addCustomer(String username, String password) {
        Customer customer = new Customer(username, password);
        DataManager.saveCustomerData(customer);
    }

    /**
     * Deletes an existing customer from the system.
     */
    public void deleteCustomer(String username) {
        DataManager.deleteCustomerData(username);
    }
}