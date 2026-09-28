public class Equipment {

    //Encapsulation
   
    private String equipmentId;
    private String name;
    private String location;
    private EquipmentStatus status;
    private double usageHours;


private static final double MAINTENANCE_THRESHOLD = 100.0;

    public Equipment(String equipmentId, String name, String location) {
    this.equipmentId = equipmentId;
    this.name = name;
    this.location = location;
    this.status = EquipmentStatus.OPERATIONAL;
    this.usageHours = 0.0;
}
public String getEquipmentId() {
    return equipmentId;
}

public String getName() {
    return name;
}

public String getLocation() {
    return location;
}

public EquipmentStatus getStatus() {
    return status;
}

public double getUsageHours() {
    return usageHours;
}

public void setName(String name) {
    this.name = name;
}

public void setLocation(String location) {
    this.location = location;
}

public void setStatus(EquipmentStatus status) {
    this.status = status;
}
public void addUsageHours(double hours) {
    if (hours > 0) {
        usageHours += hours;
    }
}

public boolean needsMaintenance() {
    return usageHours >= MAINTENANCE_THRESHOLD;
}

public void deactivate() {
    this.status = EquipmentStatus.INACTIVE;
}

}