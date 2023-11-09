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

    public static List<User> getUsersByAttribute(String attributeToSearch) {
        List<User> usersByAttribute = new ArrayList<>();
        User userTemp = null;

        for(User user: usersList) {
            if(user.getFirstName().contains(attributeToSearch)) {
                userTemp = user;
                usersByAttribute.add(userTemp);
            }

            if(user.getLastName().contains(attributeToSearch)) {
                userTemp = user;
                usersByAttribute.add(userTemp);
            }

            if(user.getPhoneNumber().contains(attributeToSearch)) {
                userTemp = user;
                usersByAttribute.add(userTemp);
            }

        }

        return usersByAttribute;
    }

    public static void addNewUserToDatabase(String firstName, String lastName, String phoneNumber) {
        usersList.add(new User(firstName, lastName, phoneNumber));
    }

}
