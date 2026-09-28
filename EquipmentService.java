import java.util.List;

// SERVICE LAYER
// Handles the business operations related to equipment.

public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    // Business Operation - Add new equipment
public void addEquipment(String equipmentId, String name, String location)
        throws DuplicateDataException {

    Equipment equipment =
            new Equipment(equipmentId, name, location);

    equipmentRepository.add(equipment);
}

public List<Equipment> getAllEquipment() {
    return equipmentRepository.findAll();
}

public Equipment findEquipment(String equipmentId) {
    return equipmentRepository.findById(equipmentId);
}

//Edit equipment details
public boolean editEquipment(
        String equipmentId,
        String newName,
        String newLocation) {

    Equipment equipment =
            equipmentRepository.findById(equipmentId);

    if (equipment == null) {
        return false;
    }

    equipment.setName(newName);
    equipment.setLocation(newLocation);

    return true;
}

// Deactivate equipment
public boolean deactivateEquipment(String equipmentId) {

    Equipment equipment =
            equipmentRepository.findById(equipmentId);

    if (equipment == null) {
        return false;
    }

    equipment.deactivate();

    return true;
}

//Record equipment usage
public boolean recordUsage(String equipmentId, double hours) {

    Equipment equipment =
            equipmentRepository.findById(equipmentId);

    if (equipment == null) {
        return false;
    }

    equipment.addUsageHours(hours);

    return true;
}

//Check preventive maintenance requirement
public boolean needsMaintenance(String equipmentId) {

    Equipment equipment =
            equipmentRepository.findById(equipmentId);

    if (equipment == null) {
        return false;
    }

    return equipment.needsMaintenance();
}
}