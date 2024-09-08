package coe528.project;

public interface State {
    void purchase(Customer customer, double amount) throws Exception;
}
