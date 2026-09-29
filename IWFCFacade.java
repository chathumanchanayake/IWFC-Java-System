// DESIGN PATTERN: Facade Pattern (Structural)
// Provides one simplified entry point to the main services of the IWFC system.

public class IWFCFacade {

    private final EquipmentService equipmentService;
    private final FitnessSessionService sessionService;
    private final MaintenanceService maintenanceService;

    public IWFCFacade(
            EquipmentService equipmentService,
            FitnessSessionService sessionService,
            MaintenanceService maintenanceService) {

        this.equipmentService = equipmentService;
        this.sessionService = sessionService;
        this.maintenanceService = maintenanceService;
    }



// Provides a simple way for the UI to add equipment without directly communicating with EquipmentService.
public void addEquipment(
        String equipmentId,
        String name,
        String location)
        throws DuplicateDataException {

    equipmentService.addEquipment(
            equipmentId,
            name,
            location
    );
}


// Provides access to the equipment inventory.
public java.util.List<Equipment> getAllEquipment() {

    return equipmentService.getAllEquipment();
}


// Records usage for a piece of equipment.
public boolean recordEquipmentUsage(
        String equipmentId,
        double hours) {

    return equipmentService.recordUsage(
            equipmentId,
            hours
    );
}


// SESSION OPERATIONS

public void scheduleSession(
        String sessionId,
        String sessionName,
        String studio,
        java.time.LocalDateTime startTime,
        java.time.LocalDateTime endTime,
        String instructorId,
        String equipmentId)
        throws DuplicateDataException,
        InvalidBookingException {

    sessionService.scheduleSession(
            sessionId,
            sessionName,
            studio,
            startTime,
            endTime,
            instructorId,
            equipmentId
    );
}

public void bookSession(
        String sessionId,
        String memberId)
        throws InvalidBookingException {

    sessionService.bookSession(
            sessionId,
            memberId
    );
}


// MAINTENANCE OPERATIONS

// Provides a simplified way to report an equipment fault.
public void reportFault(
        String requestId,
        String equipmentId,
        String description,
        UrgencyLevel urgency,
        String reportedBy)
        throws DuplicateDataException {

    maintenanceService.reportFault(
            requestId,
            equipmentId,
            description,
            urgency,
            reportedBy
    );
}

// Assigns a pending maintenance request.
public void assignMaintenance(
        String requestId,
        String assignedTo)
        throws InvalidMaintenanceStateException {

    maintenanceService.assignMaintenance(
            requestId,
            assignedTo
    );
}

// Completes an assigned maintenance request.
public void completeMaintenance(
        String requestId)
        throws InvalidMaintenanceStateException {

    maintenanceService.completeMaintenance(
            requestId
    );
}

// Provides controlled access to the global maintenance log.
public java.util.List<MaintenanceRequest> getMaintenanceLog(
        User requestingUser)
        throws UnauthorizedAccessException {

    return maintenanceService.getAllRequests(
            requestingUser
    );
}
}