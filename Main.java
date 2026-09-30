import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner =
                new Scanner(System.in);

// REPOSITORIES
EquipmentRepository equipmentRepository =
        new EquipmentRepository();

FitnessSessionRepository sessionRepository =
        new FitnessSessionRepository();


// SERVICES
EquipmentService equipmentService =
        new EquipmentService(
                equipmentRepository
        );

FitnessSessionService sessionService =
        new FitnessSessionService(
                sessionRepository
        );

MaintenanceService maintenanceService =
        new MaintenanceService(
                equipmentService
        );

UserService userService =
        new UserService();


// DESIGN PATTERN: Facade Pattern
// Provides one simplified access point to the main system services.
IWFCFacade iwfcFacade =
        new IWFCFacade(
                equipmentService,
                sessionService,
                maintenanceService
        );

// CREATE INITIAL SYSTEM USERS
// DESIGN PATTERN: Factory Pattern (Creational)
User admin =
        UserFactory.createUser(
                "Administrator",
                "A001",
                "Alex"
        );

User instructor =
        UserFactory.createUser(
                "Instructor",
                "I001",
                "Sam"
        );

User member =
        UserFactory.createUser(
                "Member",
                "M001",
                "Taylor"
        );

// REGISTER INITIAL USERS
try {

    userService.addUser(
            admin,
            admin
    );

    userService.addUser(
            admin,
            instructor
    );

    userService.addUser(
            admin,
            member
    );

} catch (DuplicateDataException |
         UnauthorizedAccessException e) {

    System.out.println(
            "ERROR: " + e.getMessage()
    );
}

// The application starts with the Administrator logged in.
User currentUser = admin;

// DESIGN PATTERN: Observer Pattern (Behavioural)
// Administrator and Instructor receive maintenance updates.
maintenanceService.addObserver(
        admin
);

maintenanceService.addObserver(
        instructor
);

        System.out.println(
                "=========================================="
        );

        System.out.println(
                " Intelligent Wellness and Fitness Center"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println();
        System.out.println(
                "System started successfully."
        );

        System.out.println();

System.out.println(
        "Current User: "
        + currentUser.getName()
        + " ("
        + currentUser.getRole()
        + ")"
);
    
      // MAIN APPLICATION MENU
boolean running = true;

while (running) {

    System.out.println();
    System.out.println(
            "=========================================="
    );
    System.out.println(
            "              MAIN MENU"
    );
    System.out.println(
            "=========================================="
    );

    System.out.println(
            "Current User: "
            + currentUser.getName()
            + " ("
            + currentUser.getRole()
            + ")"
    );

    System.out.println();
    System.out.println("1. Equipment Management");
    System.out.println("2. Session Management");
    System.out.println("3. Booking Management");
    System.out.println("4. Maintenance Management");
    System.out.println("5. User Account Management");
    System.out.println("6. Change User");
    System.out.println("0. Exit");

    System.out.println();
    System.out.print("Choose an option: ");

    int choice =
            scanner.nextInt();

    switch (choice) {

        case 1:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "          EQUIPMENT MANAGEMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    System.out.println("1. View Equipment");
    System.out.println("2. Add Equipment");
    System.out.println("3. Edit Equipment");
    System.out.println("4. Deactivate Equipment");
    System.out.println("5. Record Equipment Usage");
    System.out.println("0. Back");

    System.out.println();
    System.out.print(
            "Choose an option: "
    );

    int equipmentChoice =
            scanner.nextInt();

    switch (equipmentChoice) {

        case 1:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "           EQUIPMENT INVENTORY"
    );
    System.out.println(
            "------------------------------------------"
    );

    if (equipmentService
            .getAllEquipment()
            .isEmpty()) {

        System.out.println(
                "No equipment available."
        );

    } else {

        for (Equipment equipment :
                equipmentService.getAllEquipment()) {

            System.out.println(
                    "ID: "
                    + equipment.getEquipmentId()
            );

            System.out.println(
                    "Name: "
                    + equipment.getName()
            );

            System.out.println(
                    "Location: "
                    + equipment.getLocation()
            );

            System.out.println(
                    "Status: "
                    + equipment.getStatus()
            );

            System.out.println(
                    "Usage Hours: "
                    + equipment.getUsageHours()
            );

            System.out.println(
                    "------------------------------------------"
            );
        }
    }

    break;

        case 2:

    // ACCESS CONTROL
    // Only Administrators can add equipment.
    if (!(currentUser instanceof Administrator)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "can add equipment."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "              ADD EQUIPMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    // Clear the remaining newline from nextInt().
    scanner.nextLine();

    System.out.print(
            "Enter Equipment ID: "
    );
    String equipmentId =
            scanner.nextLine();

    System.out.print(
            "Enter Equipment Name: "
    );
    String equipmentName =
            scanner.nextLine();

    System.out.print(
            "Enter Location: "
    );
    String equipmentLocation =
            scanner.nextLine();

    try {

        equipmentService.addEquipment(
        equipmentId,
        equipmentName,
        equipmentLocation
);

        System.out.println();
        System.out.println(
                "Equipment added successfully."
        );

    } catch (DuplicateDataException e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );
    }

    break;

        case 3:

    // ACCESS CONTROL
    // Only Administrators can edit equipment.
    if (!(currentUser instanceof Administrator)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "can edit equipment."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "              EDIT EQUIPMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Equipment ID: "
    );
    String editEquipmentId =
            scanner.nextLine();

    Equipment equipmentToEdit =
            equipmentService.findEquipment(
                    editEquipmentId
            );

    if (equipmentToEdit == null) {

        System.out.println(
                "ERROR: Equipment "
                + editEquipmentId
                + " was not found."
        );

        break;
    }

    System.out.println(
            "Current Name: "
            + equipmentToEdit.getName()
    );

    System.out.println(
            "Current Location: "
            + equipmentToEdit.getLocation()
    );

    System.out.println();

    System.out.print(
            "Enter New Name: "
    );
    String newEquipmentName =
            scanner.nextLine();

    System.out.print(
            "Enter New Location: "
    );
    String newEquipmentLocation =
            scanner.nextLine();

    equipmentService.editEquipment(
            editEquipmentId,
            newEquipmentName,
            newEquipmentLocation
    );

    System.out.println();
    System.out.println(
            "Equipment updated successfully."
    );

    break;

        case 4:

    // ACCESS CONTROL
    // Only Administrators can deactivate equipment.
    if (!(currentUser instanceof Administrator)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "can deactivate equipment."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "          DEACTIVATE EQUIPMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Equipment ID: "
    );

    String deactivateEquipmentId =
            scanner.nextLine();

    Equipment equipmentToDeactivate =
            equipmentService.findEquipment(
                    deactivateEquipmentId
            );

    if (equipmentToDeactivate == null) {

        System.out.println(
                "ERROR: Equipment "
                + deactivateEquipmentId
                + " was not found."
        );

        break;
    }

    equipmentService.deactivateEquipment(
            deactivateEquipmentId
    );

    System.out.println();
    System.out.println(
            deactivateEquipmentId
            + " deactivated successfully."
    );

    break;

        case 5:

    // ACCESS CONTROL
    // Administrators and Instructors can record equipment usage.
    if (!(currentUser instanceof Administrator)
            && !(currentUser instanceof Instructor)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "or Instructors can record equipment usage."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "          RECORD EQUIPMENT USAGE"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Equipment ID: "
    );

    String usageEquipmentId =
            scanner.nextLine();

    Equipment usageEquipment =
            equipmentService.findEquipment(
                    usageEquipmentId
            );

    if (usageEquipment == null) {

        System.out.println(
                "ERROR: Equipment "
                + usageEquipmentId
                + " was not found."
        );

        break;
    }

    System.out.print(
            "Enter Usage Hours: "
    );

    double usageHours =
            scanner.nextDouble();

    equipmentService.recordUsage(
            usageEquipmentId,
            usageHours
    );

    System.out.println();
    System.out.println(
            usageHours
            + " hours recorded successfully."
    );

    System.out.println(
            "Total Usage Hours: "
            + usageEquipment.getUsageHours()
    );

    if (equipmentService.needsMaintenance(
            usageEquipmentId)) {

        System.out.println(
                "MAINTENANCE ALERT: "
                + usageEquipmentId
                + " has reached the service threshold."
        );
    }

    break;

        case 0:
            System.out.println(
                    "Returning to Main Menu..."
            );
            break;

        default:
            System.out.println(
                    "Invalid equipment option."
            );
    }

    break;

        case 2:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "           SESSION MANAGEMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    System.out.println("1. View Sessions");
    System.out.println("2. Schedule Session");
    System.out.println("0. Back");

    System.out.println();
    System.out.print(
            "Choose an option: "
    );

    int sessionChoice =
            scanner.nextInt();

    switch (sessionChoice) {

        case 1:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "            SCHEDULED SESSIONS"
    );
    System.out.println(
            "------------------------------------------"
    );

    if (sessionService
            .getAllSessions()
            .isEmpty()) {

        System.out.println(
                "No sessions available."
        );

    } else {

        for (FitnessSession session :
                sessionService.getAllSessions()) {

            System.out.println(
                    "Session ID: "
                    + session.getSessionId()
            );

            System.out.println(
                    "Session Name: "
                    + session.getSessionName()
            );

            System.out.println(
                    "Studio: "
                    + session.getStudio()
            );

            System.out.println(
                    "Instructor ID: "
                    + session.getInstructorId()
            );

            System.out.println(
                    "Equipment ID: "
                    + session.getEquipmentId()
            );

            System.out.println(
                    "Start Time: "
                    + session.getStartTime()
            );

            System.out.println(
                    "End Time: "
                    + session.getEndTime()
            );

            System.out.println(
                    "------------------------------------------"
            );
        }
    }

    break;

        case 2:

    // ACCESS CONTROL
    // Administrators and Instructors can schedule sessions.
    if (!(currentUser instanceof Administrator)
            && !(currentUser instanceof Instructor)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "or Instructors can schedule sessions."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "             SCHEDULE SESSION"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Session ID: "
    );
    String sessionId =
            scanner.nextLine();

    System.out.print(
            "Enter Session Name: "
    );
    String sessionName =
            scanner.nextLine();

    System.out.print(
            "Enter Studio: "
    );
    String studio =
            scanner.nextLine();

    System.out.print(
            "Enter Equipment ID: "
    );
    String sessionEquipmentId =
            scanner.nextLine();

    System.out.print(
            "Enter Start Time "
            + "(YYYY-MM-DDTHH:MM): "
    );

    String startInput =
            scanner.nextLine();

    System.out.print(
            "Enter End Time "
            + "(YYYY-MM-DDTHH:MM): "
    );

    String endInput =
            scanner.nextLine();

    try {

        LocalDateTime startTime =
                LocalDateTime.parse(
                        startInput
                );

        LocalDateTime endTime =
                LocalDateTime.parse(
                        endInput
                );

        sessionService.scheduleSession(
                sessionId,
                sessionName,
                studio,
                startTime,
                endTime,
                currentUser.getUserId(),
                sessionEquipmentId
        );

        System.out.println();
        System.out.println(
                "Session scheduled successfully."
        );

    } catch (DuplicateDataException |
             InvalidBookingException e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );

    } catch (
            java.time.format.DateTimeParseException e) {

        System.out.println(
                "ERROR: Invalid date/time format."
        );
    }

    break;

        case 0:
            System.out.println(
                    "Returning to Main Menu..."
            );
            break;

        default:
            System.out.println(
                    "Invalid session option."
            );
    }

    break;

        case 3:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "           BOOKING MANAGEMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    System.out.println("1. Book Session");
    System.out.println("2. View My Bookings");
    System.out.println("0. Back");

    System.out.println();
    System.out.print(
            "Choose an option: "
    );

    int bookingChoice =
            scanner.nextInt();

    switch (bookingChoice) {

        case 1:

    // ACCESS CONTROL
    // Only Members can book fitness sessions.
    if (!(currentUser instanceof Member)) {

        System.out.println(
                "ACCESS DENIED: Only Members "
                + "can book sessions."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "              BOOK SESSION"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Session ID: "
    );

    String bookingSessionId =
            scanner.nextLine();

    try {

        sessionService.bookSession(
                bookingSessionId,
                currentUser.getUserId()
        );

        System.out.println();
        System.out.println(
                currentUser.getName()
                + " successfully booked "
                + bookingSessionId
                + "."
        );

    } catch (InvalidBookingException e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );
    }

    break;

        case 2:

    // ACCESS CONTROL
    // Only Members have personal session bookings.
    if (!(currentUser instanceof Member)) {

        System.out.println(
                "ACCESS DENIED: Only Members "
                + "can view personal bookings."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "              MY BOOKINGS"
    );
    System.out.println(
            "------------------------------------------"
    );

    java.util.List<FitnessSession> memberBookings =
            sessionService.getSessionsForMember(
                    currentUser.getUserId()
            );

    if (memberBookings.isEmpty()) {

        System.out.println(
                "No bookings found."
        );

    } else {

        for (FitnessSession bookedSession :
                memberBookings) {

            System.out.println(
                    "Session ID: "
                    + bookedSession.getSessionId()
            );

            System.out.println(
                    "Session Name: "
                    + bookedSession.getSessionName()
            );

            System.out.println(
                    "Studio: "
                    + bookedSession.getStudio()
            );

            System.out.println(
                    "Start Time: "
                    + bookedSession.getStartTime()
            );

            System.out.println(
                    "End Time: "
                    + bookedSession.getEndTime()
            );

            System.out.println(
                    "------------------------------------------"
            );
        }
    }

    break;

        case 0:
            System.out.println(
                    "Returning to Main Menu..."
            );
            break;

        default:
            System.out.println(
                    "Invalid booking option."
            );
    }

    break;

        case 4:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "         MAINTENANCE MANAGEMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    System.out.println("1. Report Equipment Fault");
    System.out.println("2. View Maintenance Log");
    System.out.println("3. Assign Maintenance");
    System.out.println("4. Complete Maintenance");
    System.out.println("0. Back");

    System.out.println();
    System.out.print(
            "Choose an option: "
    );

    int maintenanceChoice =
            scanner.nextInt();

    switch (maintenanceChoice) {

       case 1:

    // ACCESS CONTROL
    // Administrators and Instructors can report faults.
    if (!(currentUser instanceof Administrator)
            && !(currentUser instanceof Instructor)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "or Instructors can report faults."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "          REPORT EQUIPMENT FAULT"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Maintenance Request ID: "
    );
    String requestId =
            scanner.nextLine();

    System.out.print(
            "Enter Equipment ID: "
    );
    String faultEquipmentId =
            scanner.nextLine();

    System.out.print(
            "Enter Fault Description: "
    );
    String faultDescription =
            scanner.nextLine();

    System.out.println();
    System.out.println("Select Urgency:");
    System.out.println("1. LOW");
    System.out.println("2. MEDIUM");
    System.out.println("3. HIGH");

    System.out.print(
            "Choose urgency: "
    );

    int urgencyChoice =
            scanner.nextInt();

    UrgencyLevel urgency = null;

    switch (urgencyChoice) {

        case 1:
            urgency = UrgencyLevel.LOW;
            break;

        case 2:
            urgency = UrgencyLevel.MEDIUM;
            break;

        case 3:
            urgency = UrgencyLevel.HIGH;
            break;

        default:
            System.out.println(
                    "ERROR: Invalid urgency selection."
            );
            break;
    }

    if (urgency == null) {
    break;
}

    try {

        maintenanceService.reportFault(
                requestId,
                faultEquipmentId,
                faultDescription,
                urgency,
                currentUser.getUserId()
        );

        System.out.println();
        System.out.println(
                "Fault reported successfully."
        );

        System.out.println(
                "Equipment "
                + faultEquipmentId
                + " status changed to FAULTY."
        );

    } catch (DuplicateDataException e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );
    }

    break;

        case 2:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "            MAINTENANCE LOG"
    );
    System.out.println(
            "------------------------------------------"
    );

    try {

        java.util.List<MaintenanceRequest> maintenanceLog =
                maintenanceService.getAllRequests(
                        currentUser
                );

        if (maintenanceLog.isEmpty()) {

            System.out.println(
                    "No maintenance requests available."
            );

        } else {

            for (MaintenanceRequest request :
                    maintenanceLog) {

                System.out.println(
                        "Request ID: "
                        + request.getRequestId()
                );

                System.out.println(
                        "Equipment ID: "
                        + request.getEquipmentId()
                );

                System.out.println(
                        "Description: "
                        + request.getDescription()
                );

                System.out.println(
                        "Urgency: "
                        + request.getUrgency()
                );

                System.out.println(
                        "Status: "
                        + request.getStatus()
                );

                System.out.println(
                        "Reported By: "
                        + request.getReportedBy()
                );

                System.out.println(
                        "Assigned To: "
                        + request.getAssignedTo()
                );

                System.out.println(
                        "------------------------------------------"
                );
            }
        }

    } catch (UnauthorizedAccessException e) {

        System.out.println(
                "ACCESS DENIED: "
                + e.getMessage()
        );
    }

    break;

        case 3:

    // ACCESS CONTROL
    // Only Administrators can assign maintenance work.
    if (!(currentUser instanceof Administrator)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "can assign maintenance."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "           ASSIGN MAINTENANCE"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Maintenance Request ID: "
    );

    String assignRequestId =
            scanner.nextLine();

    System.out.print(
            "Enter Technician Name: "
    );

    String technicianName =
            scanner.nextLine();

    try {

        maintenanceService.assignMaintenance(
                assignRequestId,
                technicianName
        );

        System.out.println();
        System.out.println(
                "Maintenance assigned successfully."
        );

    } catch (InvalidMaintenanceStateException e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );
    }

    break;

        case 4:

    // ACCESS CONTROL
    // Only Administrators can complete maintenance work.
    if (!(currentUser instanceof Administrator)) {

        System.out.println(
                "ACCESS DENIED: Only Administrators "
                + "can complete maintenance."
        );

        break;
    }

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "          COMPLETE MAINTENANCE"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter Maintenance Request ID: "
    );

    String completeRequestId =
            scanner.nextLine();

    try {

        maintenanceService.completeMaintenance(
                completeRequestId
        );

        System.out.println();
        System.out.println(
                "Maintenance completed successfully."
        );

    } catch (InvalidMaintenanceStateException e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );
    }

    break;

        case 0:
            System.out.println(
                    "Returning to Main Menu..."
            );
            break;

        default:
            System.out.println(
                    "Invalid maintenance option."
            );
    }

    break;

        case 5:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "        USER ACCOUNT MANAGEMENT"
    );
    System.out.println(
            "------------------------------------------"
    );

    System.out.println("1. View Users");
    System.out.println("2. Add User");
    System.out.println("3. Find User");
    System.out.println("0. Back");

    System.out.println();
    System.out.print(
            "Choose an option: "
    );

    int userManagementChoice =
            scanner.nextInt();

    switch (userManagementChoice) {

        case 1:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "             SYSTEM USERS"
    );
    System.out.println(
            "------------------------------------------"
    );

    if (userService
            .getAllUsers()
            .isEmpty()) {

        System.out.println(
                "No users registered."
        );

    } else {

        for (User registeredUser :
                userService.getAllUsers()) {

            System.out.println(
                    "User ID: "
                    + registeredUser.getUserId()
            );

            System.out.println(
                    "Name: "
                    + registeredUser.getName()
            );

            System.out.println(
                    "Role: "
                    + registeredUser.getRole()
            );

            System.out.println(
                    "------------------------------------------"
            );
        }
    }

    break;

       case 2:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "               ADD USER"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter User ID: "
    );
    String newUserId =
            scanner.nextLine();

    System.out.print(
            "Enter User Name: "
    );
    String newUserName =
            scanner.nextLine();

    System.out.println();
    System.out.println("Select User Role:");
    System.out.println("1. Administrator");
    System.out.println("2. Instructor");
    System.out.println("3. Member");

    System.out.print(
            "Choose role: "
    );

    int roleChoice =
            scanner.nextInt();

    String newUserRole = null;

    switch (roleChoice) {

        case 1:
            newUserRole = "Administrator";
            break;

        case 2:
            newUserRole = "Instructor";
            break;

        case 3:
            newUserRole = "Member";
            break;

        default:
            System.out.println(
                    "ERROR: Invalid role selection."
            );
            break;
    }
    if (newUserRole == null) {
    break;
}

    if (roleChoice < 1 ||
            roleChoice > 3) {

        break;
    }

    try {

        // DESIGN PATTERN: Factory Pattern
        User newUser =
                UserFactory.createUser(
                        newUserRole,
                        newUserId,
                        newUserName
                );

        userService.addUser(
                currentUser,
                newUser
        );

        System.out.println();
        System.out.println(
                "User added successfully."
        );

        System.out.println(
                newUser.getUserId()
                + " - "
                + newUser.getName()
                + " - "
                + newUser.getRole()
        );

    } catch (DuplicateDataException |
             UnauthorizedAccessException e) {

        System.out.println(
                "ERROR: " + e.getMessage()
        );
    }

    break;

        case 3:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "               FIND USER"
    );
    System.out.println(
            "------------------------------------------"
    );

    scanner.nextLine();

    System.out.print(
            "Enter User ID: "
    );

    String searchUserId =
            scanner.nextLine();

    User foundUser =
            userService.findUser(
                    searchUserId
            );

    if (foundUser == null) {

        System.out.println(
                "ERROR: User "
                + searchUserId
                + " was not found."
        );

    } else {

        System.out.println();
        System.out.println(
                "User found successfully."
        );

        System.out.println(
                "User ID: "
                + foundUser.getUserId()
        );

        System.out.println(
                "Name: "
                + foundUser.getName()
        );

        System.out.println(
                "Role: "
                + foundUser.getRole()
        );
    }

    break;

        case 0:
            System.out.println(
                    "Returning to Main Menu..."
            );
            break;

        default:
            System.out.println(
                    "Invalid user management option."
            );
    }

    break;

        case 6:

    System.out.println();
    System.out.println(
            "------------------------------------------"
    );
    System.out.println(
            "              CHANGE USER"
    );
    System.out.println(
            "------------------------------------------"
    );

    System.out.println("1. Alex (Administrator)");
    System.out.println("2. Sam (Instructor)");
    System.out.println("3. Taylor (Member)");
    System.out.println("0. Cancel");

    System.out.println();
    System.out.print(
            "Select user: "
    );

    int userChoice =
            scanner.nextInt();

    switch (userChoice) {

        case 1:
            currentUser = admin;

            System.out.println(
                    "Switched to Alex (Administrator)."
            );
            break;

        case 2:
            currentUser = instructor;

            System.out.println(
                    "Switched to Sam (Instructor)."
            );
            break;

        case 3:
            currentUser = member;

            System.out.println(
                    "Switched to Taylor (Member)."
            );
            break;

        case 0:
            System.out.println(
                    "User change cancelled."
            );
            break;

        default:
            System.out.println(
                    "Invalid user selection."
            );
    }

    break;

        case 0:
            running = false;

            System.out.println();
            System.out.println(
                    "Exiting IWFC system..."
            );
            break;

        default:
            System.out.println(
                    "Invalid option. Please try again."
            );
    }
}
        scanner.close();
    }
}