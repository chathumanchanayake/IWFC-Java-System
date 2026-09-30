import java.time.LocalDateTime;
import java.util.List;

public class PrototypeDemo {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println(" Intelligent Wellness and Fitness Center");
        System.out.println("==========================================");


        // ==================================================
        // USER AND POLYMORPHISM TEST
        // ==================================================

        User admin = new Administrator("A001", "Alex");
        User instructor = new Instructor("I001", "Sam");
        User member = new Member("M001", "Taylor");

        System.out.println();
        System.out.println("System Users");
        System.out.println("------------------------------------------");

        displayUser(admin);
        displayUser(instructor);
        displayUser(member);


        // ==================================================
        // EQUIPMENT OBJECT TEST
        // ==================================================

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


        // ==================================================
        // EQUIPMENT REPOSITORY AND EXCEPTION TEST
        // ==================================================

        System.out.println();
        System.out.println("Equipment Repository Test");
        System.out.println("------------------------------------------");

        EquipmentRepository repository =
                new EquipmentRepository();

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

            // Intentional duplicate ID
            // to test custom exception handling.
            equipmentService.addEquipment(
                    "E001",
                    "Rowing Machine",
                    "Cardio Zone"
            );

        } catch (DuplicateDataException e) {

            System.out.println(
                    "ERROR: " + e.getMessage()
            );

        } finally {

            System.out.println(
                    "Equipment add operation completed."
            );
        }


        // ==================================================
        // DISPLAY CURRENT EQUIPMENT INVENTORY
        // ==================================================

        System.out.println();
        System.out.println("Current Equipment Inventory");
        System.out.println("------------------------------------------");

        for (Equipment equipmentItem
                : equipmentService.getAllEquipment()) {

            System.out.println(
                    equipmentItem.getEquipmentId() + " - " +
                    equipmentItem.getName() + " - " +
                    equipmentItem.getLocation() + " - " +
                    equipmentItem.getStatus()
            );
        }


        // ==================================================
        // EQUIPMENT SERVICE OPERATIONS TEST
        // ==================================================

        System.out.println();
        System.out.println("Equipment Service Operations Test");
        System.out.println("------------------------------------------");


        // Edit E002
        boolean edited =
                equipmentService.editEquipment(
                        "E002",
                        "Premium Spin Bike",
                        "Studio A"
                );

        if (edited) {

            System.out.println(
                    "E002 updated successfully."
            );

        } else {

            System.out.println(
                    "E002 could not be found."
            );
        }


        // Record usage for E001
        boolean usageRecorded =
                equipmentService.recordUsage(
                        "E001",
                        25.5
                );

        if (usageRecorded) {

            System.out.println(
                    "25.5 usage hours recorded for E001."
            );

        } else {

            System.out.println(
                    "E001 could not be found."
            );
        }


        // Deactivate E002
        boolean deactivated =
                equipmentService.deactivateEquipment(
                        "E002"
                );

        if (deactivated) {

            System.out.println(
                    "E002 deactivated successfully."
            );

        } else {

            System.out.println(
                    "E002 could not be found."
            );
        }


        // ==================================================
        // UPDATED EQUIPMENT INVENTORY
        // ==================================================

        System.out.println();
        System.out.println("Updated Equipment Inventory");
        System.out.println("------------------------------------------");

        for (Equipment equipmentItem
                : equipmentService.getAllEquipment()) {

            System.out.println(
                    equipmentItem.getEquipmentId() + " - " +
                    equipmentItem.getName() + " - " +
                    equipmentItem.getLocation() + " - " +
                    equipmentItem.getStatus() + " - " +
                    equipmentItem.getUsageHours() +
                    " hours"
            );
        }


        // ==================================================
        // PREVENTIVE MAINTENANCE ALERT TEST
        // ==================================================

        System.out.println();
        System.out.println("Preventive Maintenance Test");
        System.out.println("------------------------------------------");

        equipmentService.recordUsage(
                "E001",
                80.0
        );

        Equipment equipment =
                equipmentService.findEquipment("E001");

        System.out.println(
                "E001 Total Usage: " +
                equipment.getUsageHours() +
                " hours"
        );

        if (equipmentService.needsMaintenance("E001")) {

            System.out.println(
                    "MAINTENANCE ALERT: " +
                    "E001 has reached the service threshold."
            );

        } else {

            System.out.println(
                    "E001 does not currently require " +
                    "preventive maintenance."
            );
        }


        // ==================================================
        // FITNESS SESSION SCHEDULING TEST
        // ==================================================

        System.out.println();
        System.out.println("Fitness Session Scheduling Test");
        System.out.println("------------------------------------------");

        FitnessSessionRepository sessionRepository =
                new FitnessSessionRepository();

        FitnessSessionService sessionService =
                new FitnessSessionService(
                        sessionRepository
                );

        try {

            // Valid session
            sessionService.scheduleSession(
                    "S001",
                    "Yoga",
                    "Studio A",
                    LocalDateTime.of(
                            2026, 10, 1, 10, 0
                    ),
                    LocalDateTime.of(
                            2026, 10, 1, 11, 0
                    ),
                    "I001",
                    "E001"
            );

            System.out.println(
                    "S001 Yoga scheduled successfully."
            );


            // Valid session because it uses another studio
            sessionService.scheduleSession(
                    "S002",
                    "Pilates",
                    "Studio B",
                    LocalDateTime.of(
                            2026, 10, 1, 10, 30
                    ),
                    LocalDateTime.of(
                            2026, 10, 1, 11, 30
                    ),
                    "I001",
                    "E002"
            );

            System.out.println(
                    "S002 Pilates scheduled successfully."
            );

        } catch (
                DuplicateDataException |
                InvalidBookingException e) {

            System.out.println(
                    "ERROR: " + e.getMessage()
            );
        }

        System.out.println();
System.out.println("Outside Operating Hours Test");
System.out.println("------------------------------------------");

try {

    System.out.println(
            "Attempting to schedule a session at 5:00 AM..."
    );

    sessionService.scheduleSession(
        "S005",
        "Early Morning Yoga",
        "Studio C",
        LocalDateTime.of(2026, 10, 1, 5, 0),
        LocalDateTime.of(2026, 10, 1, 6, 0),
        "I001",
        "E001"
);

    System.out.println(
            "Session scheduled successfully."
    );

} catch (
        DuplicateDataException |
        InvalidBookingException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}


        // ==================================================
        // STUDIO DOUBLE-BOOKING TEST
        // ==================================================

        System.out.println();
        System.out.println(
                "Testing studio double-booking..."
        );

        try {

            // Invalid because Studio A is already being
            // used by S001 between 10:00 and 11:00.
            sessionService.scheduleSession(
                    "S003",
                    "Zumba",
                    "Studio A",
                    LocalDateTime.of(
                            2026, 10, 1, 10, 30
                    ),
                    LocalDateTime.of(
                            2026, 10, 1, 11, 30
                    ),
                    "I002",
                    "E002"
            );

            System.out.println(
                    "S003 Zumba scheduled successfully."
            );

        } catch (
                DuplicateDataException |
                InvalidBookingException e) {

            System.out.println(
                    "ERROR: " + e.getMessage()
            );
        }

        // ==================================================
// EQUIPMENT DOUBLE-BOOKING TEST
// ==================================================

System.out.println();
System.out.println(
        "Testing equipment double-booking..."
);

try {

    // Studio C is free, but E001 is already used
    // by S001 between 10:00 and 11:00.
    sessionService.scheduleSession(
            "S004",
            "Cardio Training",
            "Studio C",
            LocalDateTime.of(
                    2026, 10, 1, 10, 15
            ),
            LocalDateTime.of(
                    2026, 10, 1, 10, 45
            ),
            "I002",
            "E001"
    );

    System.out.println(
            "S004 Cardio Training scheduled successfully."
    );

} catch (
        DuplicateDataException |
        InvalidBookingException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}


        // ==================================================
        // DISPLAY SUCCESSFULLY SCHEDULED SESSIONS
        // ==================================================

        System.out.println();
        System.out.println("Scheduled Sessions");
        System.out.println("------------------------------------------");

        for (FitnessSession session
                : sessionService.getAllSessions()) {

            System.out.println(
        session.getSessionId() + " - " +
        session.getSessionName() + " - " +
        session.getStudio() + " - " +
        "Equipment: " + session.getEquipmentId() + " - " +
        session.getStartTime() + " to " +
        session.getEndTime()
);
        }

        // ==================================================
// MEMBER BOOKING TEST
// ==================================================

System.out.println();
System.out.println("Member Booking Test");
System.out.println("------------------------------------------");

try {

    sessionService.bookSession(
            "S001",
            "M001"
    );

    System.out.println(
            "M001 successfully booked S001."
    );

} catch (InvalidBookingException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

System.out.println();
System.out.println("Testing duplicate member booking...");

try {

    sessionService.bookSession(
            "S001",
            "M001"
    );

    System.out.println(
            "M001 successfully booked S001."
    );

} catch (InvalidBookingException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

System.out.println();
System.out.println("Testing member time conflict...");

try {

    // M001 already booked S001 from 10:00 to 11:00.
    // S002 runs from 10:30 to 11:30.
    sessionService.bookSession(
            "S002",
            "M001"
    );

    System.out.println(
            "M001 successfully booked S002."
    );

} catch (InvalidBookingException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

System.out.println();
System.out.println("Bookings for M001");
System.out.println("------------------------------------------");

for (FitnessSession bookedSession
        : sessionService.getSessionsForMember("M001")) {

    System.out.println(
            bookedSession.getSessionId() + " - " +
            bookedSession.getSessionName() + " - " +
            bookedSession.getStudio() + " - " +
            bookedSession.getStartTime()
    );
}

// ==================================================
// MAINTENANCE WORKFLOW TEST
// ==================================================

System.out.println();
System.out.println("Maintenance Workflow Test");
System.out.println("------------------------------------------");

MaintenanceService maintenanceService =
        new MaintenanceService(equipmentService);

// DESIGN PATTERN: Observer Pattern
// Administrator and Instructor subscribe to maintenance status notifications.
User notificationAdmin =
        new Administrator("A004", "Alex");

User notificationInstructor =
        new Instructor("I004", "Sam");

maintenanceService.addObserver(notificationAdmin);
maintenanceService.addObserver(notificationInstructor);

// DESIGN PATTERN: Facade Pattern
// Main can use one simplified interface to access the major IWFC subsystems.
IWFCFacade iwfcFacade =
        new IWFCFacade(
                equipmentService,
                sessionService,
                maintenanceService
        );

try {

    // Instructor reports a fault.
    maintenanceService.reportFault(
            "MR001",
            "E001",
            "Treadmill belt is making an unusual noise.",
            UrgencyLevel.HIGH,
            "I001"
    );

    MaintenanceRequest request =
            maintenanceService.findRequest("MR001");

    System.out.println("Fault reported successfully.");
    System.out.println(
            "Request: " + request.getRequestId()
    );
    System.out.println(
            "Reported By: " + request.getReportedBy()
    );
    System.out.println(
            "Urgency: " + request.getUrgency()
    );
    System.out.println(
            "Request Status: " + request.getStatus()
    );
    System.out.println(
            "Equipment Status: " +
            equipmentService
                    .findEquipment("E001")
                    .getStatus()
    );


    // Administrator assigns maintenance.
    System.out.println();
    System.out.println("Assigning maintenance...");

    maintenanceService.assignMaintenance(
            "MR001",
            "Technician John"
    );

    System.out.println(
            "Assigned To: " + request.getAssignedTo()
    );
    System.out.println(
            "Request Status: " + request.getStatus()
    );
    System.out.println(
            "Equipment Status: " +
            equipmentService
                    .findEquipment("E001")
                    .getStatus()
    );


    // Maintenance work is completed.
    System.out.println();
    System.out.println("Completing maintenance...");

    maintenanceService.completeMaintenance(
            "MR001"
    );

    System.out.println(
            "Request Status: " + request.getStatus()
    );
    System.out.println(
            "Equipment Status: " +
            equipmentService
                    .findEquipment("E001")
                    .getStatus()
    );

} catch (
        DuplicateDataException |
        InvalidMaintenanceStateException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );

} catch (IllegalArgumentException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

System.out.println();
System.out.println("Invalid Maintenance Transition Test");
System.out.println("------------------------------------------");

try {

    System.out.println(
            "Attempting to assign completed request MR001..."
    );

    maintenanceService.assignMaintenance(
            "MR001",
            "Technician Sarah"
    );

} catch (InvalidMaintenanceStateException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

// ==================================================
// AUTHORIZATION TEST
// ==================================================

System.out.println();
System.out.println("Maintenance Log Authorization Test");
System.out.println("------------------------------------------");

// OOP CONCEPT: Polymorphism
// Both variables use the User reference type,
// but contain different user objects.
User adminUser =
        new Administrator("A002", "Alex");

User memberUser =
        new Member("M002", "Taylor");


// Test Administrator access
try {

    System.out.println(
            "Alex attempting to access maintenance log..."
    );

    List<MaintenanceRequest> maintenanceLog =
            maintenanceService.getAllRequests(adminUser);

    System.out.println(
            "ACCESS GRANTED: Administrator can view maintenance log."
    );

    for (MaintenanceRequest request : maintenanceLog) {

        System.out.println(
                request.getRequestId() +
                " - Equipment: " +
                request.getEquipmentId() +
                " - " +
                request.getStatus()
        );
    }

} catch (UnauthorizedAccessException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}


// Test Member access
try {

    System.out.println();
    System.out.println(
            "Taylor attempting to access maintenance log..."
    );

    maintenanceService.getAllRequests(memberUser);

    System.out.println(
            "ACCESS GRANTED"
    );

} catch (UnauthorizedAccessException e) {

    System.out.println(
            "ACCESS DENIED: " + e.getMessage()
    );
}

// ==================================================
// FACTORY PATTERN TEST
// ==================================================

System.out.println();
System.out.println("Factory Pattern Test");
System.out.println("------------------------------------------");

User factoryAdmin =
        UserFactory.createUser(
                "Administrator",
                "A003",
                "Factory Alex"
        );

User factoryInstructor =
        UserFactory.createUser(
                "Instructor",
                "I003",
                "Factory Sam"
        );

User factoryMember =
        UserFactory.createUser(
                "Member",
                "M003",
                "Factory Taylor"
        );

displayUser(factoryAdmin);
displayUser(factoryInstructor);
displayUser(factoryMember);

System.out.println();
System.out.println("Facade Pattern Test");
System.out.println("------------------------------------------");

try {

    iwfcFacade.addEquipment(
            "E003",
            "Rowing Machine",
            "Cardio Zone"
    );

    System.out.println(
            "E003 added successfully through IWFCFacade."
    );

} catch (DuplicateDataException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

System.out.println();
System.out.println("Equipment accessed through Facade:");

for (Equipment item :
        iwfcFacade.getAllEquipment()) {

    System.out.println(
            item.getEquipmentId()
            + " - "
            + item.getName()
            + " - "
            + item.getStatus()
    );
}

System.out.println();
System.out.println("User Account Management Test");
System.out.println("------------------------------------------");

UserService userService =
        new UserService();

try {

    // DESIGN PATTERN: Factory Pattern
    // Create different types of users through UserFactory.
    User accountAdmin =
            UserFactory.createUser(
                    "Administrator",
                    "A010",
                    "Alex"
            );

   User accountInstructor =
            UserFactory.createUser(
                    "Instructor",
                    "I010",
                    "Sam"
            );

    User accountMember =
            UserFactory.createUser(
                    "Member",
                    "M010",
                    "Taylor"
            );


    // Add users to the system.
   userService.addUser(
        accountAdmin,
        accountAdmin
);

userService.addUser(
        accountAdmin,
        accountInstructor
);

userService.addUser(
        accountAdmin,
        accountMember
);


    // Display all registered users.
    System.out.println("Registered Users:");

    for (User registeredUser :
            userService.getAllUsers()) {

        System.out.println(
                registeredUser.getUserId()
                + " - "
                + registeredUser.getName()
                + " - "
                + registeredUser.getRole()
        );
    }


    // Deliberately attempt to add a duplicate user ID.
    System.out.println();
    System.out.println(
            "Testing duplicate user ID..."
    );

    User duplicateUser =
            UserFactory.createUser(
                    "Member",
                    "M010",
                    "Another Taylor"
            );

    userService.addUser(
        accountAdmin,
        duplicateUser
);

} catch (DuplicateDataException |
       UnauthorizedAccessException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

System.out.println();
System.out.println(
        "Testing unauthorized user account access..."
);

try {

    User unauthorizedUser =
            UserFactory.createUser(
                    "Member",
                    "M011",
                    "New Member"
            );

    // Taylor is a Member, so this should be rejected.
    User requestingMember =
        UserFactory.createUser(
                "Member",
                "M010",
                "Taylor"
        );

userService.addUser(
        requestingMember,
        unauthorizedUser
);

} catch (DuplicateDataException |
         UnauthorizedAccessException e) {

    System.out.println(
            "ACCESS DENIED: " + e.getMessage()
    );
}

    } // End of main()


    // ==================================================
    // OOP CONCEPT: POLYMORPHISM
    // ==================================================

    // The same User reference can represent
    // Administrator, Instructor or Member objects.
    // Java calls the correct overridden getRole()
    // method at runtime.
    public static void displayUser(User user) {

        System.out.println(
                user.getUserId() + " - " +
                user.getName() + " - " +
                user.getRole()
        );
    }

} // End of Main class