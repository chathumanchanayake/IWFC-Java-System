import java.util.ArrayList;
import java.util.List;


// Responsible for managing IWFC user accounts.
public class UserService {

    // Encapsulation
    // The list of users is kept private inside the service.
    private final List<User> users =
            new ArrayList<>();


    // Adds a new user account to the system.
    // Only an Administrator is allowed to perform this operation.
    public void addUser(
            User requestingUser,
            User user)
            throws DuplicateDataException,
            UnauthorizedAccessException {

        // ACCESS CONTROL
        // Only Administrators can create user accounts.
        if (!(requestingUser instanceof Administrator)) {

            throw new UnauthorizedAccessException(
                    requestingUser.getName()
                    + " is not authorized to add user accounts."
            );
        }

        // Check whether the user ID already exists.
        for (User existingUser : users) {

            if (existingUser.getUserId()
                    .equalsIgnoreCase(user.getUserId())) {

                throw new DuplicateDataException(
                        "User with ID "
                        + user.getUserId()
                        + " already exists."
                );
            }
        }

        users.add(user);
    }


    // Finds a user using their unique user ID.
    public User findUser(String userId) {

        for (User user : users) {

            if (user.getUserId()
                    .equalsIgnoreCase(userId)) {

                return user;
            }
        }

        return null;
    }


    // Returns a copy of all registered users.
    public List<User> getAllUsers() {

        return new ArrayList<>(users);
    }
}