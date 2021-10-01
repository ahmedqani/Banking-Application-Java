import controller.UserController;
import model.User;
import model.UserRole;

import java.util.List;

public class MainSystem {

    public static void main(String[] args) {
        System.out.println("Main Method that Runs Application");
        UserController userController = new UserController();
        List<User> users = userController.getAllUsers();

//        User user1 = new User("TestUser5","TestPass2", UserRole.EMPLOYEE,20,true);
//
//        userController.saveUser(user1);
        for (User user: users){
            System.out.println(user);
        }
        System.out.println("-----------------------");
        System.out.println(userController.getUser(6));
        userController.deleteUser(6);
        for (User user: users){
            System.out.println(user);
        }
        System.out.println(userController.getUser(6));

    }
}
