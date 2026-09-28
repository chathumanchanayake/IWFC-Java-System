//Abstraction


public abstract class User {

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

}