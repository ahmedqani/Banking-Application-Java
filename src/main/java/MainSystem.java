import controller.AccountsController;
import controller.TransactionController;
import controller.UserController;
import exceptions.InvalidCredentialsException;
import logging.Logging;
import model.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class MainSystem {
    private static final UserController userController = new UserController();
    private static final TransactionController transactionController = new TransactionController();
    private static final AccountsController accountController = new AccountsController();

    public static void main(String[] args) throws Exception, InvalidCredentialsException {
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
                    currentUser = userController.loginUser(username, password);
                    System.out.println(currentUser);
                    loggedin = true;
                    continue;
                } else {
                    System.out.print("Please enter your username: ");
                    String username = in.nextLine();
                    System.out.print("Please enter your password: ");
                    String password = in.nextLine();

                    try {
                        User temp = new User(username, password, UserRole.USER);
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
                if (currentUser.getUserRole().equalsIgnoreCase("user")) {
                    System.out.printf("Welcome User %s To Logout please Press 1 !\n", currentUser.getUsername());
                    System.out.print("Press 2 To Lookup your Accounts and start a new Transaction!\n");
                    System.out.print("Press 3 To check all of your previous Transactions!\n");

                    int choice = Integer.parseInt(in.nextLine());
                    if (choice == 1) {
                        System.out.println("Logging Out Thanks for Visiting GoodBy!");
                        loggedin = false;
                        done = true;
                    }
                    if (transactionOption(in, currentUser, choice)) continue;
                }
                if (currentUser.getUserRole().equalsIgnoreCase("employee")) {
                    System.out.printf("Welcome Employee %s To Logout please Press 1 !\n", currentUser.getUsername());
                    System.out.print("Press 2 To Lookup your Accounts and start a new Transaction!\n");
                    System.out.print("Press 3 To check all of your previous Transactions!\n");
                    System.out.println("Press 4 to Checkout Employee Options");

                    int choice = Integer.parseInt(in.nextLine());
                    if (choice == 1) {
                        System.out.println("Logging Out Thanks for Visiting GoodBye!");
                        loggedin = false;
                        done = true;
                    }
                    if (transactionOption(in, currentUser, choice)) continue;
                    if (employeeOption(in, currentUser, choice)) continue;
                }
                if (currentUser.getUserRole().equalsIgnoreCase("admin")) {
                    System.out.printf("Welcome Admin %s To Logout please Press 1 \n", currentUser.getUsername());
                    System.out.println("Press 2 To Lookup your Accounts and start a new Transaction!");
                    System.out.println("Press 3 To check all of your previous Transactions!");
                    System.out.println("Press 4 to Checkout Admin Options");
                    int choice = Integer.parseInt(in.nextLine());
                    if (choice == 1) {
                        System.out.println("Logging Out Thanks for Visiting GoodBye!");
                        Logging.logger.info("User: " + currentUser.getUsername() + " was logged OUT");
                        loggedin = false;
                        done = true;
                    }
                    if (transactionOption(in, currentUser, choice)) continue;
                    if (adminOption(in, currentUser, choice)) continue;

                }


            }
        }
    }

    private static boolean transactionOption(Scanner in, User currentUser, int choice) throws Exception {
        if (choice == 2) {
            List<Accounts> userAccounts = accountController.getUserAccounts(currentUser.getUsername());
            if (userAccounts.isEmpty()) {
                System.out.println("You Currently dont have any Accounts Please create one");
                System.out.println("1 -> Checking account");
                System.out.println("2 -> Saving account");
                System.out.println("1 -> Shared account");
                int aChoice = Integer.parseInt(in.nextLine());
                if (aChoice == 1) {
                    Accounts checkingAccount = new Accounts(AccountType.CHECKING, false, 0, currentUser.getUsername(), null, false);
                    accountController.saveAccount(checkingAccount);
                }
                if (aChoice == 2) {
                    Accounts savingAccount = new Accounts(AccountType.SAVING, false, 0, currentUser.getUsername(), null, false);
                    accountController.saveAccount(savingAccount);
                }
                if (aChoice == 3) {
                    Accounts checkingAccount = new Accounts(AccountType.CHECKING, true, 0, currentUser.getUsername(), null, false);
                    accountController.saveAccount(checkingAccount);
                }
            } else {
                System.out.println("Here is a list of all of your Current Accounts!");
                System.out.println("Please Pick an Account by typing the Account ID");
                for (Accounts accounts : userAccounts) {
                    System.out.println(accounts);
                }
                int bankChoice = Integer.parseInt(in.nextLine());
                Accounts chosenBank = accountController.getAccountById(bankChoice);
                if (chosenBank.getAccount_id() != 0) {
                    if (chosenBank.isAccountIsActive()) {
                        System.out.println("Your Current Balance is >>");
                        System.out.println(chosenBank.getBalance());
                        System.out.println("please select 1 > Transfer; 2 > Withdrew; 3 > deposit");
                        int type = Integer.parseInt(in.nextLine());
                        if (type == 1) {
                            System.out.println("You have selected to Transfer Please type the Amount:");
                            long amount = Long.parseLong(in.nextLine());
                            System.out.println("Username to send to:");
                            String userToSendToName = in.nextLine();
                            User userToSendTo = userController.getUserByName(userToSendToName);
                            if (userToSendTo.getUsername() != null) {
                                System.out.println("Here is a list of all of their Current Accounts!");
                                System.out.println("Please Pick an Account to transfer to by typing the Account ID");
                                List<Accounts> otherUserAccounts = accountController.getUserAccounts(userToSendTo.getUsername());
                                for (Accounts accounts : otherUserAccounts) {
                                    System.out.println(accounts);
                                }
                                int accountTo = Integer.parseInt(in.nextLine());
                                Accounts accountToSendTo = accountController.getAccountById(accountTo);
                                Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.TRANSFER, userToSendTo.getUsername());
                                accountController.updateAccountBalance(accountToSendTo, chosenBank, transaction);
                            } else {
                                System.out.println("User Couldn't be found please try again!");
                                return true;
                            }
                        }
                        if (type == 2) {
                            System.out.println("You have selected to Withdrew Please type the Amount:");
                            long amount = Long.parseLong(in.nextLine());
                            Accounts atm = accountController.getAccountById(1);
                            Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.WITHDRAW);
                            accountController.updateAccountBalance(atm, chosenBank, transaction);
                            return true;
                        }
                        if (type == 3) {
                            System.out.println("Select the amount to Deposit");
                            long amount = Long.parseLong(in.nextLine());
                            Accounts atm = accountController.getAccountById(1);
                            Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.DEPOSIT);
                            accountController.updateAccountBalance(atm, chosenBank, transaction);
                            return true;
                        } else {
                            System.out.println("Exiting this current Menu! ");
                            return true;
                        }
                    } else {
                        System.out.println("Account has not been activated yet or has been closed!");
                        return true;
                    }
                } else {
                    System.out.println("Please make sure you entered the right Account ID!!");
                }
            }
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
            System.out.printf("Welcome %s to the Admin Panel \n", currentUser.getUsername());
            System.out.println("please select 1 > Accounts Management; 2 > get all Transactions");
            int adminChoice = Integer.parseInt(in.nextLine());
            //Add Admin Options
            if (adminChoice == 1) {
                System.out.println("Here is a list of all the accounts");
                List<User> allUsers = userController.getAllUsers();
                for (User user : allUsers) {
                    System.out.println(user);
                }
                System.out.println("Please Select a Username :");
                String username = in.nextLine();
                User userToEdit = userController.getUserByName(username);
                System.out.printf("Here is a list of all %s accounts\n", userToEdit.getUsername());
                System.out.println("Please Pick an Account by typing the Account ID");
                List<Accounts> allAccounts = accountController.getUserAccounts(userToEdit.getUsername());
                for (Accounts account : allAccounts) {
                    System.out.println(account);
                }
                int bankChoice = Integer.parseInt(in.nextLine());
                Accounts chosenBank = accountController.getAccountById(bankChoice);
                while (chosenBank != null) {
                    System.out.println("What would you like to do with this user!:");
                    System.out.println("1 > Approve Accounts");
                    System.out.println("2 > Deny new Accounts");
                    System.out.println("3 > Cancel Accounts");
                    System.out.println("4 > Change Accounts Role");
                    System.out.println("5 > Manage Accounts");
                    System.out.println("6 > Exit this Menu");
                    int managedAccountChoice = Integer.parseInt(in.nextLine());
                    if (managedAccountChoice == 1) {
                        chosenBank.setActive("TRUE");
                        accountController.activateAccount(chosenBank);
                    }
                    if (managedAccountChoice == 2 || managedAccountChoice == 3) {
                        if (!chosenBank.isAccountIsActive()) {
                            System.out.println("Accounts is already disabled");
                        } else {
                            chosenBank.setActive("FALSE");
                            accountController.activateAccount(chosenBank);
                        }
                    }
                    if (managedAccountChoice == 4) {
                        System.out.println("1-> User");
                        System.out.println("2-> Employee");
                        System.out.println("3-> Admin");
                        int roleChoice = Integer.parseInt(in.nextLine());
                        if (roleChoice == 1) {
                            userToEdit.setUserRole("USER");
                            userController.updateUserRole(userToEdit);
                        }
                        if (roleChoice == 2) {
                            userToEdit.setUserRole("employee");
                            userController.updateUserRole(userToEdit);
                        }
                        if (roleChoice == 3) {
                            userToEdit.setUserRole("admin");
                            userController.updateUserRole(userToEdit);
                        } else {
                            System.out.println("Exiting this current Menu! ");
                            return true;
                        }
                    }
                    if (managedAccountChoice == 5) {
                        if (chosenBank.isAccountIsActive()) {
                            System.out.println("Account's Current Balance is >>");
                            System.out.println(chosenBank.getBalance());
                            System.out.println("please select 1 > Transfer; 2 > Withdrew; 3 > deposit");
                            int type = Integer.parseInt(in.nextLine());
                            if (type == 1) {
                                System.out.println("You have selected to Transfer Please type the Amount:");
                                long amount = Long.parseLong(in.nextLine());
                                System.out.println("Username to send to:");
                                String userToSendToName = in.nextLine();
                                User userToSendTo = userController.getUserByName(userToSendToName);
                                if (userToSendTo.getUsername() != null) {
                                    System.out.println("Here is a list of all of their Current Accounts!");
                                    System.out.println("Please Pick an Account to transfer to by typing the Account ID");
                                    System.out.println(userToSendTo.getAccounts());
                                    int accountTo = Integer.parseInt(in.nextLine());
                                    Accounts accountToSendTo = accountController.getAccountById(accountTo);
                                    Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.TRANSFER, userToSendTo.getUsername());
                                    accountController.updateAccountBalance(accountToSendTo, chosenBank, transaction);
                                } else {
                                    System.out.println("User Couldn't be found please try again!");
                                    return true;
                                }
                            }
                            if (type == 2) {
                                System.out.println("You have selected to Withdrew Please type the Amount:");
                                long amount = Long.parseLong(in.nextLine());
                                Accounts atm = accountController.getAccountById(1);
                                Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.WITHDRAW);
                                accountController.updateAccountBalance(atm, chosenBank, transaction);
                                return true;
                            }
                            if (type == 3) {
                                System.out.println("Select the amount to Deposit");
                                long amount = Long.parseLong(in.nextLine());
                                Accounts atm = accountController.getAccountById(1);
                                Transaction transaction = new Transaction(amount, currentUser.getUsername(), TransactionType.DEPOSIT);
                                accountController.updateAccountBalance(atm, chosenBank, transaction);
                                return true;
                            } else {
                                System.out.println("Exiting this current Menu! ");
                                return true;
                            }
                        } else {
                            System.out.println("Account has not been activated yet or has been closed!");
                            System.out.println("Exiting this current Menu! ");
                            return true;
                        }
                    }
                    if (managedAccountChoice == 6) {
                        chosenBank = null;
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

    private static boolean employeeOption(Scanner in, User currentUser, int choice) throws Exception {
        if (choice == 4) {
            System.out.printf("Welcome %s to the Employee Panel \n", currentUser.getUsername());
            System.out.println("please select 1 > Accounts Management; 2 > get all Transactions");
            int empChoice = Integer.parseInt(in.nextLine());
            //Add Employee Options
            if (empChoice == 1) {
                System.out.println("Here is a list of all the accounts");
                List<User> allUsers = userController.getAllUsers();
                for (User user : allUsers) {
                    System.out.println(user);
                }
                System.out.println("Please Select a Username :");
                String username = in.nextLine();
                User userToEdit = userController.getUserByName(username);
                System.out.printf("Here is a list of all %s accounts\n", userToEdit.getUsername());
                List<Accounts> allAccounts = accountController.getUserAccounts(userToEdit.getUsername());
                for (Accounts account : allAccounts) {
                    System.out.println(account);
                }
                System.out.println("Please Pick an Account by typing the Account ID");
                System.out.println(currentUser.getAccounts());
                int bankChoice = Integer.parseInt(in.nextLine());
                Accounts chosenBank = accountController.getAccountById(bankChoice);
                while (chosenBank != null) {
                    System.out.println("What would you like to do with this user!:");
                    System.out.println("1 > Approve Accounts");
                    System.out.println("2 > Deny new Accounts");
                    System.out.println("3 > Cancel Accounts");
                    System.out.println("4 > User & Account Details");
                    System.out.println("5 > Exit this Menu");
                    int managedAccountChoice = Integer.parseInt(in.nextLine());
                    if (managedAccountChoice == 1) {
                        chosenBank.setActive("TRUE");
                        accountController.activateAccount(chosenBank);
                    }
                    if (managedAccountChoice == 2 || managedAccountChoice == 3) {
                        if (!chosenBank.isAccountIsActive()) {
                            System.out.println("Accounts is already disabled");
                        } else {
                            chosenBank.setActive("FALSE");
                            accountController.activateAccount(chosenBank);
                        }
                    }
                    if (managedAccountChoice == 4) {
                        System.out.println(userToEdit);
                        System.out.println(chosenBank);
                    }
                    if (managedAccountChoice == 5) {
                        chosenBank = null;
                    }
                }
            }
            return true;
        }
        return false;
    }

}
