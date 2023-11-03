package nm.vamk.assignment6;

import java.util.ArrayList;
import java.util.List;

public class UserDB {

    static List<User> usersList = new ArrayList<>();

    static {
        usersList.add(new User("Bob", "Smith", "8888888888"));
        usersList.add(new User("Jack", "Nelson", "0321321321"));
        usersList.add(new User("Alice", "Smith", "987654321"));
    }

    public static List<User> getUsersList() {
        return usersList;
    }

    /*
    public static String[] getUsersStringArray() {
        String[] usersStringArray = null;
        String userTemp = null;
        for(User user : usersList) {
            userTemp = user.toString();
            usersStringArray.
        }

        return usersStringArray;
    }
     */

    public static void addNewUserToDatabase(String firstName, String lastName, String phoneNumber) {
        usersList.add(new User(firstName, lastName, phoneNumber));
    }

}
