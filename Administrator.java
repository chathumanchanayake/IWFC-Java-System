//Inheritance

public class Administrator extends User {

    public Administrator(String userId, String name) {
        super(userId, name);
    }

//Polymorphism

@Override
public String getRole() {
    return "Administrator";
}
}