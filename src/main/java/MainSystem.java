import controller.UserController;
import exceptions.InvalidCredentialsException;
import model.User;
import model.UserRole;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class MainSystem {
    private static final UserController userController = new UserController();

    public static void main(String[] args) {
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
                }else {
                    System.out.print("Please enter your username: ");
                    String username = in.nextLine();
                    System.out.print("Please enter your password: ");
                    String password = in.nextLine();

                    try {
                        User temp = new User(username,password,UserRole.USER,0,false);
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
            while (loggedin){
                if (currentUser.isAccountIsActive()){
                    if (currentUser.getUserRole().equalsIgnoreCase("user")){
                        System.out.printf("Welcome User %s To Logout please Press 1 anytime!",currentUser.getUsername());
                        System.out.print("Press 2");

                        int choice = Integer.parseInt(in.nextLine());
                        if(choice == 1){
                            System.out.println("Logging Out Thanks for Visiting GoodBy!");
                            loggedin = false;
                            done = true;
                        }
                    }
                    if (currentUser.getUserRole().equalsIgnoreCase("employee")){
                        System.out.printf("Welcome Employee %s To Logout please Press 1 anytime!",currentUser.getUsername());
                        int choice = Integer.parseInt(in.nextLine());
                        if(choice == 1){
                            System.out.println("Logging Out Thanks for Visiting GoodBy!");
                            loggedin = false;
                            done = true;
                        }
                    }
                    if (currentUser.getUserRole().equalsIgnoreCase("admin")){
                        System.out.printf("Welcome Admin %s To Logout please Press 1 anytime!",currentUser.getUsername());
                        int choice = Integer.parseInt(in.nextLine());
                        if(choice == 1){
                            System.out.println("Logging Out Thanks for Visiting GoodBy!");
                            loggedin = false;
                            done = true;
                        }
                    }
                }else {
                    System.out.println("Your Account isnt Active yet GoodBye!");
                    loggedin = false;
                    done = true;
                }

            }
        }
    }
}
