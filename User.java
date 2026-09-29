//Abstraction


public abstract class User
        implements NotificationObserver {

    //Encapsulation
    
    private String userId;
    private String name;

   
public User(String userId, String name) {
    this.userId = userId;
    this.name = name;
}

// Encapsulation

public String getUserId() {
    return userId;
}

public String getName() {
    return name;
}

//Abstraction

public abstract String getRole();

// DESIGN PATTERN: Observer Pattern
// Users can receive notifications from the system.
@Override
public void update(String message) {

    System.out.println(
            "NOTIFICATION for " +
            name +
            ": " +
            message
    );
}

}