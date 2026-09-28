import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// ADVANCED OOP: Implements the generic Repository contract
// specifically for Equipment objects.
public class EquipmentRepository implements Repository<Equipment> {

    // COLLECTION: HashMap
    // Equipment ID is used as the key for fast lookup.
    private final Map<String, Equipment> equipmentMap = new HashMap<>();

    @Override
    public void add(Equipment equipment) throws DuplicateDataException {

        if (equipmentMap.containsKey(equipment.getEquipmentId())) {
            throw new DuplicateDataException(
                    "Equipment with ID " +
                    equipment.getEquipmentId() +
                    " already exists."
            );
        }

        equipmentMap.put(equipment.getEquipmentId(), equipment);
    }

    @Override
    public Equipment findById(String id) {
        return equipmentMap.get(id);
    }

    @Override
    public List<Equipment> findAll() {
        return new ArrayList<>(equipmentMap.values());
    }
}