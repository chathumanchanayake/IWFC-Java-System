import java.util.ArrayList;
import java.util.List;

// Handles maintenance and fault-reporting business operations.
public class MaintenanceService {

    // Stores all maintenance requests in the system.
    private final List<MaintenanceRequest> maintenanceRequests =
            new ArrayList<>();


// MaintenanceService uses EquipmentService so maintenance actions can update equipment status.
private final EquipmentService equipmentService;

public MaintenanceService(
        EquipmentService equipmentService) {

    this.equipmentService = equipmentService;
}

    // Instructor reports an equipment fault.
    public void reportFault(
            String requestId,
            String equipmentId,
            String description,
            UrgencyLevel urgency,
            String reportedBy)
            throws DuplicateDataException {

        // Prevent duplicate maintenance request IDs.
        for (MaintenanceRequest request : maintenanceRequests) {

            if (request.getRequestId()
                    .equalsIgnoreCase(requestId)) {

                throw new DuplicateDataException(
                        "Maintenance request with ID " +
                        requestId +
                        " already exists."
                );
            }
        }

        Equipment equipment =
        equipmentService.findEquipment(equipmentId);

if (equipment == null) {

    throw new IllegalArgumentException(
            "Equipment " + equipmentId +
            " could not be found."
    );
}

        MaintenanceRequest request =
                new MaintenanceRequest(
                        requestId,
                        equipmentId,
                        description,
                        urgency,
                        reportedBy
                );

        maintenanceRequests.add(request);

        // The reported equipment is now marked as faulty.
equipment.setStatus(EquipmentStatus.FAULTY);
    }


    // Finds a maintenance request using its ID.
    public MaintenanceRequest findRequest(
            String requestId) {

        for (MaintenanceRequest request
                : maintenanceRequests) {

            if (request.getRequestId()
                    .equalsIgnoreCase(requestId)) {

                return request;
            }
        }

        return null;
    }


    // Returns all maintenance requests.
// The global maintenance log is restricted to administrators.
public List<MaintenanceRequest> getAllRequests(
        User requestingUser)
        throws UnauthorizedAccessException {

    if (!(requestingUser instanceof Administrator)) {

        throw new UnauthorizedAccessException(
                requestingUser.getName() +
                " is not authorized to access " +
                "the global maintenance log."
        );
    }

    return new ArrayList<>(maintenanceRequests);
}


// Assigns a pending maintenance request.
public void assignMaintenance(
        String requestId,
        String assignedTo)
        throws InvalidMaintenanceStateException {

    MaintenanceRequest request =
            findRequest(requestId);

    if (request == null) {

        throw new InvalidMaintenanceStateException(
                "Maintenance request " +
                requestId +
                " could not be found."
        );
    }

    request.assignTo(assignedTo);

    Equipment equipment =
        equipmentService.findEquipment(
                request.getEquipmentId()
        );

if (equipment != null) {

    equipment.setStatus(
            EquipmentStatus.UNDER_MAINTENANCE
    );
}
}

// Completes an assigned maintenance request.
public void completeMaintenance(
        String requestId)
        throws InvalidMaintenanceStateException {

    MaintenanceRequest request =
            findRequest(requestId);

    if (request == null) {

        throw new InvalidMaintenanceStateException(
                "Maintenance request " +
                requestId +
                " could not be found."
        );
    }

    request.complete();

    Equipment equipment =
        equipmentService.findEquipment(
                request.getEquipmentId()
        );

if (equipment != null) {

    equipment.setStatus(
            EquipmentStatus.OPERATIONAL
    );
}
}
}