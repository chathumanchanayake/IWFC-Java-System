import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FitnessSession {

    // OOP CONCEPT: Encapsulation
    // Session details are kept private.
    private String sessionId;
    private String sessionName;
    private String studio;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String instructorId;
    private String equipmentId;
    private final List<String> bookedMemberIds = new ArrayList<>();

    public FitnessSession(
        String sessionId,
        String sessionName,
        String studio,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String instructorId,
        String equipmentId) {

    this.sessionId = sessionId;
    this.sessionName = sessionName;
    this.studio = studio;
    this.startTime = startTime;
    this.endTime = endTime;
    this.instructorId = instructorId;
    this.equipmentId = equipmentId;
}

    public String getSessionId() {
        return sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getStudio() {
        return studio;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public String getEquipmentId() {
    return equipmentId;
}

    public List<String> getBookedMemberIds() {
    return new ArrayList<>(bookedMemberIds);
}

public boolean hasMember(String memberId) {
    return bookedMemberIds.contains(memberId);
}

public void addMember(String memberId) {
    bookedMemberIds.add(memberId);
}


// Checks whether this session overlaps with another time period.
public boolean overlapsWith(
        LocalDateTime otherStart,
        LocalDateTime otherEnd) {

    return startTime.isBefore(otherEnd)
            && endTime.isAfter(otherStart);
}

}