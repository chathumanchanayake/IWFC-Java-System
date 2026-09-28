public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println(" Intelligent Wellness and Fitness Center");
        System.out.println("==========================================");

      
        // User and Polymorphism Test
  

        User admin = new Administrator("A001", "Alex");
        User instructor = new Instructor("I001", "Sam");
        User member = new Member("M001", "Taylor");

        System.out.println();
        System.out.println("System Users");
        System.out.println("------------------------------------------");

        displayUser(admin);
        displayUser(instructor);
        displayUser(member);

      
        // Equipment Object Test
       

        System.out.println();
        System.out.println("Equipment Test");
        System.out.println("------------------------------------------");

        Equipment treadmill =
                new Equipment("E001", "Treadmill", "Cardio Zone");

        System.out.println("ID: " + treadmill.getEquipmentId());
        System.out.println("Name: " + treadmill.getName());
        System.out.println("Location: " + treadmill.getLocation());
        System.out.println("Status: " + treadmill.getStatus());
        System.out.println("Usage Hours: " + treadmill.getUsageHours());

        treadmill.addUsageHours(5.5);

        System.out.println();
        System.out.println("After recording usage:");
        System.out.println("Usage Hours: " + treadmill.getUsageHours());

        treadmill.deactivate();

        System.out.println("After deactivation:");
        System.out.println("Status: " + treadmill.getStatus());

        
        // Equipment Repository and Exception Test
        

        System.out.println();
        System.out.println("Equipment Repository Test");
        System.out.println("------------------------------------------");

        EquipmentRepository repository = new EquipmentRepository();

        EquipmentService equipmentService =
                new EquipmentService(repository);

        try {

            equipmentService.addEquipment(
                    "E001",
                    "Treadmill",
                    "Cardio Zone"
            );

            equipmentService.addEquipment(
                    "E002",
                    "Spin Bike",
                    "Cardio Zone"
            );

            System.out.println("Equipment added successfully.");

            // Intentional duplicate ID to test custom exception handling.
            equipmentService.addEquipment(
                    "E001",
                    "Rowing Machine",
                    "Cardio Zone"
            );

        } catch (DuplicateDataException e) {

            System.out.println("ERROR: " + e.getMessage());

        } finally {

            System.out.println("Equipment add operation completed.");
        }

       
        // Display Current Equipment Inventory
      

        System.out.println();
        System.out.println("Current Equipment Inventory");
        System.out.println("------------------------------------------");

        for (Equipment equipment : equipmentService.getAllEquipment()) {

            System.out.println(
                    equipment.getEquipmentId() + " - " +
                    equipment.getName() + " - " +
                    equipment.getLocation() + " - " +
                    equipment.getStatus()
            );
        }

        
// Equipment Service Operations Test


System.out.println();
System.out.println("Equipment Service Operations Test");
System.out.println("------------------------------------------");

// Edit E002
boolean edited = equipmentService.editEquipment(
        "E002",
        "Premium Spin Bike",
        "Studio A"
);

if (edited) {
    System.out.println("E002 updated successfully.");
} else {
    System.out.println("E002 could not be found.");
}

// Record usage for E001
boolean usageRecorded =
        equipmentService.recordUsage("E001", 25.5);

if (usageRecorded) {
    System.out.println("25.5 usage hours recorded for E001.");
} else {
    System.out.println("E001 could not be found.");
}

// Deactivate E002
boolean deactivated =
        equipmentService.deactivateEquipment("E002");

if (deactivated) {
    System.out.println("E002 deactivated successfully.");
} else {
    System.out.println("E002 could not be found.");
}

System.out.println();
System.out.println("Updated Equipment Inventory");
System.out.println("------------------------------------------");

for (Equipment equipment : equipmentService.getAllEquipment()) {

    System.out.println(
            equipment.getEquipmentId() + " - " +
            equipment.getName() + " - " +
            equipment.getLocation() + " - " +
            equipment.getStatus() + " - " +
            equipment.getUsageHours() + " hours"
    );
}


// Preventive Maintenance Alert Test


System.out.println();
System.out.println("Preventive Maintenance Test");
System.out.println("------------------------------------------");

equipmentService.recordUsage("E001", 80.0);

Equipment equipment =
        equipmentService.findEquipment("E001");

System.out.println(
        "E001 Total Usage: " +
        equipment.getUsageHours() +
        " hours"
);

if (equipmentService.needsMaintenance("E001")) {

    System.out.println(
            "MAINTENANCE ALERT: E001 has reached the service threshold."
    );

} else {

    System.out.println(
            "E001 does not currently require preventive maintenance."
    );
}

    } 


    // OOP CONCEPT: Polymorphism
    // The same User reference can represent different subclasses.
    // Java calls the correct overridden getRole() method at runtime.
    public static void displayUser(User user) {

        System.out.println(
                user.getUserId() + " - " +
                user.getName() + " - " +
                user.getRole()
        );
    }

} 