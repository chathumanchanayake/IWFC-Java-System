// DESIGN PATTERN: Factory Pattern (Creational)
// Centralizes the creation of different User objects.

public class UserFactory {

    public static User createUser(
            String role,
            String userId,
            String name) {

        if (role.equalsIgnoreCase("Administrator")) {

            return new Administrator(
                    userId,
                    name
            );

        } else if (role.equalsIgnoreCase("Instructor")) {

            return new Instructor(
                    userId,
                    name
            );

        } else if (role.equalsIgnoreCase("Member")) {

            return new Member(
                    userId,
                    name
            );

        } else {

            throw new IllegalArgumentException(
                    "Invalid user role: " + role
            );
        }
    }
}