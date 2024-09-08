package coe528.project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * Overview: The BankApp class is the main entry point for a JavaFX application that simulates
 * a banking system. It provides a graphical user interface for login and management of customer and manager
 * interactions within the bank.
 *
 * This class manages the different scenes of the application, including login, manager, and customer views,
 * and facilitates the operations like user authentication, customer addition and deletion, and account transactions.
 */
public class BankApp extends Application {

    private Stage primaryStage;
    private State customerLevel;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Bank Account Application");
        Scene loginScene = createLoginScene();
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private Scene createLoginScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setMaxWidth(200); // Set max width for the username field

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(200); // Set max width for the password field

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(200); // Set max width for the login button

        // Label for error messages
        Label loginErrorLabel = new Label();
        loginErrorLabel.setTextFill(Color.GREEN); // Set the error message color to green

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean success = authenticateUser(username, password);
            if (!success) {
                loginErrorLabel.setText("Incorrect username or password.");
                passwordField.clear(); // Optionally clear the password field
            } else {
                loginErrorLabel.setText(""); // Clear error message on successful login
            }
        });

        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, loginErrorLabel);

        return new Scene(layout, 300, 200);
    }
    /**
     * Adds a new customer with the given username and password.
     */
    private String addCustomer(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return "Username and password cannot be empty";
        }

        // Check if the customer already exists
        if (DataManager.customerExists(username)) {
            return "A customer with this username already exists.";
        }

        // If the customer does not exist, proceed to add them
        Manager manager = new Manager("admin", "admin"); // Use actual manager credentials if needed
        manager.addCustomer(username, password);
        return "Customer added: " + username;
    }
    /**
     * Deletes the customer with the specified username.
     */
    private String deleteCustomer(String username) {
        if (username.isEmpty()) {
            return "Username cannot be empty";
        }

        // Assuming a method in DataManager to check if the customer exists
        if (!DataManager.customerExists(username)) {
            return "Customer does not exist.";
        }

        // If the customer exists, proceed to delete them
        Manager manager = new Manager("admin", "admin"); // Use actual manager credentials if needed
        manager.deleteCustomer(username);
        return "Customer deleted: " + username;
    }
    /**
     * Switches the UI to the manager view after successful manager login.
     */
    private void switchToManagerView(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, Manager");
        Label actionLabel = new Label("Add/Delete Customer:");
        Label errorMessageLabel = new Label(); // Label for displaying error messages
        errorMessageLabel.setTextFill(Color.GREEN); // Make the error message text green

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button addButton = new Button("Add Customer");
        addButton.setOnAction(e -> {
            String message = addCustomer(usernameField.getText(), passwordField.getText());
            errorMessageLabel.setText(message); // Set error or success message
            if (message.startsWith("Customer added")) {
                usernameField.clear();
                passwordField.clear();
            }
        });

        Button deleteButton = new Button("Delete Customer");
        deleteButton.setOnAction(e -> {
            String message = deleteCustomer(usernameField.getText());
            errorMessageLabel.setText(message); // Set error or success message
            if (message.startsWith("Customer deleted")) {
                usernameField.clear();
            }
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> primaryStage.setScene(createLoginScene()));

        layout.getChildren().addAll(welcomeLabel, actionLabel, usernameField, passwordField, addButton, deleteButton, logoutButton, errorMessageLabel);

        Scene managerScene = new Scene(layout, 400, 300);
        primaryStage.setScene(managerScene);
    }
    /**
     * Switches the UI to the customer view after successful customer login.
     */
    private void switchToCustomerView(Stage primaryStage, Customer customer) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, " + customer.getUsername());
        Label balanceLabel = new Label("Balance: $" + customer.getBalance());
        Label stateLabel = new Label("State: " + customer.getState().getClass().getSimpleName()); // State label
        Label errorMessageLabel = new Label();
        errorMessageLabel.setTextFill(Color.GREEN);

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                customer.deposit(amount);
                balanceLabel.setText("Balance: $" + customer.getBalance());
                stateLabel.setText("State: " + customer.getState().getClass().getSimpleName()); // Update state
                errorMessageLabel.setText(""); // Clear error message
            } catch (NumberFormatException ex) {
                errorMessageLabel.setText("Please enter a valid amount.");
            }
        });

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                customer.withdraw(amount);
                balanceLabel.setText("Balance: $" + customer.getBalance());
                stateLabel.setText("State: " + customer.getState().getClass().getSimpleName()); // Update state
                errorMessageLabel.setText(""); // Clear error message
            } catch (NumberFormatException ex) {
                errorMessageLabel.setText("Please enter a valid amount.");
            }
        });

        Button purchaseButton = new Button("Make Purchase");
        purchaseButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount < 50) {
                    errorMessageLabel.setText("Purchase must be $50 or more.");
                    return;
                }
                customer.makePurchase(amount);
                balanceLabel.setText("Balance: $" + customer.getBalance());
                stateLabel.setText("State: " + customer.getState().getClass().getSimpleName()); // Update state
                errorMessageLabel.setText(""); // Clear error message
            } catch (NumberFormatException ex) {
                errorMessageLabel.setText("Please enter a valid amount.");
            } catch (Exception ex) {
                errorMessageLabel.setText(ex.getMessage());
            }
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> primaryStage.setScene(createLoginScene()));

        layout.getChildren().addAll(welcomeLabel, balanceLabel, stateLabel, amountField, depositButton, withdrawButton, purchaseButton, logoutButton, errorMessageLabel);

        Scene customerScene = new Scene(layout, 400, 300);
        primaryStage.setScene(customerScene);
    }

    /**
     * Authenticates a user and switches the UI based on the user role.
     */
    private boolean authenticateUser(String username, String password) {
        // Check for manager credentials
        Manager manager = DataManager.loadManagerData();
        if (manager != null && username.equals(manager.getUsername()) && password.equals(manager.getPassword())) {
            switchToManagerView(primaryStage);
            return true; // Authentication successful as manager
        } else {
            // Check for customer credentials
            Customer customer = DataManager.loadCustomerData(username);
            if (customer != null && customer.getPassword().equals(password)) {
                switchToCustomerView(primaryStage, customer);
                return true; // Authentication successful as customer
            }
        }

        // Authentication failed
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
;