import controller.TransactionController;
import controller.UserController;
import exceptions.InvalidCredentialsException;
import model.Transaction;
import model.TransactionType;
import model.User;
import model.UserRole;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class MainSystem {
    private static final UserController userController = new UserController();
    private static final TransactionController transactionController = new TransactionController();

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome To Banko!!");
        User currentUser = null;
        boolean done = false;
        boolean loggedin = false;

        while (!done) {
            if (currentUser == null) {
                System.out.println("Login or Signup? Press 1 to Login, Press 2 to SignUp");
                int choice = Integer.parseInt(in.nextLine());
                if (choice == 1) {
                    System.out.print("Please enter your username: ");
                    String username = in.nextLine();
                    System.out.print("Please enter your password: ");
                    String password = in.nextLine();
                    try {
                        currentUser = userController.loginUser(username, password);
                        System.out.println(currentUser);
                        loggedin = true;
                    } catch (InvalidCredentialsException e) {
                        System.out.println("Username or password incorrect. Goodbye");
                        done = true;
                    }
                    continue;
                } else {
                    System.out.print("Please enter your username: ");
                    String username = in.nextLine();
                    System.out.print("Please enter your password: ");
                    String password = in.nextLine();

                    try {
                        User temp = new User(username, password, UserRole.USER, 0, false);
                        currentUser = userController.saveUser(temp);
                        System.out.println("You may now login with the username: " + currentUser.getUsername());
                        currentUser = null;
                        loggedin = true;
                        continue;
                    } catch (Exception e) {
                        System.out.println("Sorry we could not process your request");
                        System.out.println("Please try again later");
                        continue;
                    }
                }
            }
            while (loggedin) {
                if (currentUser.isAccountIsActive()) {
                    if (currentUser.getUserRole().equalsIgnoreCase("user")) {
                        System.out.printf("Welcome User %s To Logout please Press 1 anytime!\n", currentUser.getUsername());
                        System.out.print("Press 2 To start a new Transaction!");
                        System.out.print("Press 3 To check all of your previous Transactions!");

                        int choice = Integer.parseInt(in.nextLine());
                        if (choice == 1) {
                            System.out.println("Logging Out Thanks for Visiting GoodBy!");
                            loggedin = false;
                            done = true;
                        }
                        if (transactionOption(in, currentUser, choice)) continue;
                    }
                    if (currentUser.getUserRole().equalsIgnoreCase("employee")) {
                        System.out.printf("Welcome Employee %s To Logout please Press 1 anytime!\n", currentUser.getUsername());
                        System.out.print("Press 2 To start a new Transaction!");
                        System.out.print("Press 3 To check all of your previous Transactions!");

                        int choice = Integer.parseInt(in.nextLine());
                        if (choice == 1) {
                            System.out.println("Logging Out Thanks for Visiting GoodBye!");
                            loggedin = false;
                            done = true;
                        }
                        if (transactionOption(in, currentUser, choice)) continue;
                    }
                    if (currentUser.getUserRole().equalsIgnoreCase("admin")) {
                        System.out.printf("Welcome Admin %s To Logout please Press 1 anytime! \n", currentUser.getUsername());
                        System.out.println("Press 2 To start a new Transaction!");
                        System.out.println("Press 3 To check all of your previous Transactions!");
                        System.out.println("Press 4 to Checkout Admin Options");
                        int choice = Integer.parseInt(in.nextLine());
                        if (choice == 1) {
                            System.out.println("Logging Out Thanks for Visiting GoodBye!");
                            loggedin = false;
                            done = true;
                        }
                        if (transactionOption(in, currentUser, choice)) continue;
                        if (adminOption(in, currentUser, choice)) continue;

                    }
                } else {
                    System.out.println("Your Account isn't Active yet GoodBye!");
                    loggedin = false;
                    done = true;
                }

            }
        }
    }

    private static boolean transactionOption(Scanner in, User currentUser, int choice) {
        if (choice == 2) {
            System.out.println("Your Current Balance is >>");
            System.out.println(currentUser.getBalance());
            System.out.println("please select 1 > Transfer; 2 > Withdrew; 3 > deposit");
            int type = Integer.parseInt(in.nextLine());
            if (type == 1) {
                System.out.println("You have selected to Transfer Please type the Amount:");
                long amount = Long.parseLong(in.nextLine());
                System.out.println("Username to send to:");
                String userToSendToName = in.nextLine();
                User userToSendTo = userController.getUserByName(userToSendToName);
                if (userToSendTo.getUsername() != null) {
                    Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.TRANSFER, userToSendTo.getUsername());
                    transactionController.saveTransaction(transaction, currentUser, userToSendTo);
                } else {
                    System.out.println("User Couldn't be found please try again!");
                    return true;
                }
            }
            if (type == 2) {
                System.out.println("You have selected to Withdrew Please type the Amount:");
                long amount = Long.parseLong(in.nextLine());
                User atm = userController.getUser(4);
                Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.WITHDRAW);
                transactionController.saveTransaction(transaction, currentUser, atm);
                return true;
            }
            if (type == 3) {
                System.out.println("Select the amount to Deposit");
                long amount = Long.parseLong(in.nextLine());
                User atm = userController.getUser(4);
                Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.DEPOSIT);
                transactionController.saveTransaction(transaction, currentUser, atm);
                return true;
            }
            return true;
        }
        if (choice == 3) {
            System.out.println("Getting all of your Transactions....");
            List<Transaction> transactions = transactionController.getTransactionsByUser(currentUser);
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
            return true;
        }
        return false;
    }

    private static boolean adminOption(Scanner in, User currentUser, int choice) throws Exception {
        if (choice == 4) {
            System.out.println("Welcome to Admin Panel");
            System.out.println("please select 1 > Account Management; 2 > get all Transactions");
            int adminChoice = Integer.parseInt(in.nextLine());
            //Add Admin Options
            if (adminChoice == 1) {
                System.out.println("Here is a list of all the accounts that needs to be Approved");
                List<User> allUsers = userController.getAllUsers();
                for (User user:allUsers){
                    System.out.println(user);
                }
                System.out.println("Please Select a Username :");
                String username = in.nextLine();
                User userToEdit = userController.getUserByName(username);
                while (userToEdit != null){
                    System.out.println("What would you like to do with this user!:");
                    System.out.println("1 > Approve Account");
                    System.out.println("2 > Deny new Account");
                    System.out.println("3 > Cancel Account");
                    System.out.println("4 > Manage Account");
                    System.out.println("5 > Exit this Menu");
                    int managedAccountChoice = Integer.parseInt(in.nextLine());
                    if (managedAccountChoice == 1){
                        userToEdit.setAccountIsActive(true);
                        userController.isActive(userToEdit);
                    }
                    if (managedAccountChoice == 2 || managedAccountChoice == 3){
                        if (!userToEdit.isAccountIsActive()){
                            System.out.println("Account is already disabled");
                        }else {
                            userToEdit.setAccountIsActive(false);
                            userController.isActive(userToEdit);
                        }
                    }
                    if (managedAccountChoice == 4){
                            System.out.println("Account's Current Balance is >>");
                            System.out.println(userToEdit.getBalance());
                            System.out.println("please select 1 > Transfer; 2 > Withdrew; 3 > deposit");
                            int type = Integer.parseInt(in.nextLine());
                            if (type == 1) {
                                System.out.println("You have selected to Transfer Please type the Amount:");
                                long amount = Long.parseLong(in.nextLine());
                                System.out.println("Username to send to:");
                                String userToSendToName = in.nextLine();
                                User userToSendTo = userController.getUserByName(userToSendToName);
                                if (userToSendTo.getUsername() != null) {
                                    Transaction transaction = new Transaction(amount, userToEdit.getUsername(), TransactionType.TRANSFER, userToSendTo.getUsername());
                                    transactionController.saveTransaction(transaction, userToEdit, userToSendTo);
                                } else {
                                    System.out.println("User Couldn't be found please try again!");
                                    return true;
                                }
                            }
                            if (type == 2) {
                                System.out.println("You have selected to Withdrew Please type the Amount:");
                                long amount = Long.parseLong(in.nextLine());
                                User atm = userController.getUser(4);
                                Transaction transaction = new Transaction(amount, userToEdit.getUsername(), TransactionType.WITHDRAW);
                                transactionController.saveTransaction(transaction, userToEdit, atm);
                            }
                            if (type == 3) {
                                System.out.println("Select the amount to Deposit");
                                long amount = Long.parseLong(in.nextLine());
                                User atm = userController.getUser(4);
                                Transaction transaction = new Transaction(amount, userToEdit.getUsername(), TransactionType.DEPOSIT);
                                transactionController.saveTransaction(transaction, userToEdit, atm);
                            }
                    }
                    if (managedAccountChoice == 5){
                        userToEdit = null;
                    }
                }
            }
            if (adminChoice == 2) {
                System.out.println("Getting all Transactions....");
                List<Transaction> allTransactions = transactionController.getAllTransactions();
                for (Transaction transaction : allTransactions) {
                    System.out.println(transaction);
                }
            }
            return true;
        }
        return false;
    }

}
