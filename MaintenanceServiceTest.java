import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// JUNIT TEST CLASS
// Tests maintenance workflow and status transitions.
public class MaintenanceServiceTest {

    @Test
    void shouldCompleteValidMaintenanceWorkflow()
            throws DuplicateDataException,
            InvalidMaintenanceStateException {

        // Arrange
        EquipmentRepository equipmentRepository =
                new EquipmentRepository();

        EquipmentService equipmentService =
                new EquipmentService(
                        equipmentRepository
                );

        equipmentService.addEquipment(
                "E001",
                "Treadmill",
                "Cardio Zone"
        );

        MaintenanceService maintenanceService =
                new MaintenanceService(
                        equipmentService
                );


        // Act - Report fault
        maintenanceService.reportFault(
                "MR001",
                "E001",
                "Treadmill belt is making noise.",
                UrgencyLevel.HIGH,
                "I001"
        );

        MaintenanceRequest request =
                maintenanceService.findRequest(
                        "MR001"
                );

        // Assert initial state
        assertEquals(
                MaintenanceStatus.PENDING,
                request.getStatus()
        );


        // Act - Assign maintenance
        maintenanceService.assignMaintenance(
                "MR001",
                "Technician John"
        );

        // Assert assigned state
        assertEquals(
                MaintenanceStatus.ASSIGNED,
                request.getStatus()
        );


        // Act - Complete maintenance
        maintenanceService.completeMaintenance(
                "MR001"
        );

        // Assert completed state
        assertEquals(
                MaintenanceStatus.COMPLETED,
                request.getStatus()
        );
    }

    @Test
void shouldRejectCompletingPendingMaintenance()
        throws DuplicateDataException {

    // Arrange
    EquipmentRepository equipmentRepository =
            new EquipmentRepository();

    EquipmentService equipmentService =
            new EquipmentService(
                    equipmentRepository
            );

    equipmentService.addEquipment(
            "E001",
            "Treadmill",
            "Cardio Zone"
    );

    MaintenanceService maintenanceService =
            new MaintenanceService(
                    equipmentService
            );

    maintenanceService.reportFault(
            "MR001",
            "E001",
            "Treadmill belt is making noise.",
            UrgencyLevel.HIGH,
            "I001"
    );


    // Act + Assert
    // The request is still PENDING.
    // It cannot move directly to COMPLETED.
    assertThrows(
            InvalidMaintenanceStateException.class,
            () -> maintenanceService.completeMaintenance(
                    "MR001"
            )
    );
}

@Test
void shouldRejectMemberAccessToMaintenanceLog()
        throws DuplicateDataException {

    // Arrange
    EquipmentRepository equipmentRepository =
            new EquipmentRepository();

    EquipmentService equipmentService =
            new EquipmentService(
                    equipmentRepository
            );

    equipmentService.addEquipment(
            "E001",
            "Treadmill",
            "Cardio Zone"
    );

    MaintenanceService maintenanceService =
            new MaintenanceService(
                    equipmentService
            );

    maintenanceService.reportFault(
            "MR001",
            "E001",
            "Treadmill belt is making noise.",
            UrgencyLevel.HIGH,
            "I001"
    );

    User member =
            new Member(
                    "M001",
                    "Taylor"
            );


    // Act + Assert
    // Members are not authorized to access
    // the global maintenance log.
    assertThrows(
            UnauthorizedAccessException.class,
            () -> maintenanceService.getAllRequests(
                    member
            )
    );
}
}
