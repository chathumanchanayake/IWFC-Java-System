public class MaintenanceRequest {

    // OOP CONCEPT: Encapsulation
    // Maintenance request data is kept private.
    private String requestId;
    private String equipmentId;
    private String description;
    private UrgencyLevel urgency;
    private MaintenanceStatus status;
    private String reportedBy;
    private String assignedTo;

    public MaintenanceRequest(
            String requestId,
            String equipmentId,
            String description,
            UrgencyLevel urgency,
            String reportedBy) {

        this.requestId = requestId;
        this.equipmentId = equipmentId;
        this.description = description;
        this.urgency = urgency;
        this.reportedBy = reportedBy;

        // Every new fault begins as PENDING.
        this.status = MaintenanceStatus.PENDING;

        // Nobody has been assigned yet.
        this.assignedTo = null;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public String getDescription() {
        return description;
    }

    public UrgencyLevel getUrgency() {
        return urgency;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }


// Assigns this maintenance request to a technician/staff member.
// A maintenance request can only be assigned while it is PENDING.
public void assignTo(String assignedTo)
        throws InvalidMaintenanceStateException {

    if (status != MaintenanceStatus.PENDING) {

        throw new InvalidMaintenanceStateException(
                "Only a pending maintenance request can be assigned."
        );
    }

    this.assignedTo = assignedTo;
    this.status = MaintenanceStatus.ASSIGNED;
}

// Marks an assigned maintenance request as completed.
// A maintenance request can only be completed after it has been assigned.
public void complete()
        throws InvalidMaintenanceStateException {

    if (status != MaintenanceStatus.ASSIGNED) {

        throw new InvalidMaintenanceStateException(
                "Only an assigned maintenance request can be completed."
        );
    }

    this.status = MaintenanceStatus.COMPLETED;
}
}