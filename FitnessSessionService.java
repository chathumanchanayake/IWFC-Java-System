import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalTime;

public class FitnessSessionService {

   
//Sessions must be scheduled between 6:00 AM and 10:00 PM.
private static final LocalTime OPENING_TIME =
        LocalTime.of(6, 0);

private static final LocalTime CLOSING_TIME =
        LocalTime.of(22, 0);

    private final FitnessSessionRepository sessionRepository;

    public FitnessSessionService(
            FitnessSessionRepository sessionRepository) {

        this.sessionRepository = sessionRepository;
    }

    // Schedule a new fitness session
public void scheduleSession(
        String sessionId,
        String sessionName,
        String studio,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String instructorId,
        String equipmentId)
        throws DuplicateDataException, InvalidBookingException {

    // Validate that the session ends after it starts.
    if (!endTime.isAfter(startTime)) {

        throw new InvalidBookingException(
                "Session end time must be after the start time."
        );
    }

    
// Sessions cannot be scheduled outside the fitness center's operating hours.
if (startTime.toLocalTime().isBefore(OPENING_TIME)
        || endTime.toLocalTime().isAfter(CLOSING_TIME)) {

    throw new InvalidBookingException(
            "Session must be scheduled between "
            + OPENING_TIME +
            " and " +
            CLOSING_TIME +
            "."
    );
}


// Check existing sessions for studio conflicts.
for (FitnessSession existingSession
        : sessionRepository.findAll()) {

    boolean sameStudio =
            existingSession.getStudio()
                    .equalsIgnoreCase(studio);

    boolean timeOverlap =
            existingSession.overlapsWith(
                    startTime,
                    endTime
            );
    boolean sameEquipment =
        existingSession.getEquipmentId()
                .equalsIgnoreCase(equipmentId);

   if (sameStudio && timeOverlap) {

    throw new InvalidBookingException(
            studio + " is already booked during this time."
    );
}

if (sameEquipment && timeOverlap) {

    throw new InvalidBookingException(
            "Equipment " + equipmentId +
            " is already booked during this time."
    );
}
}

    FitnessSession newSession =
        new FitnessSession(
                sessionId,
                sessionName,
                studio,
                startTime,
                endTime,
                instructorId,
                equipmentId
        );

    sessionRepository.add(newSession);
}

public List<FitnessSession> getAllSessions() {
    return sessionRepository.findAll();
}

// Member books an existing fitness session
public void bookSession(
        String sessionId,
        String memberId)
        throws InvalidBookingException {

    FitnessSession session =
            sessionRepository.findById(sessionId);

    // Check whether the requested session exists.
    if (session == null) {

        throw new InvalidBookingException(
                "Session " + sessionId + " does not exist."
        );
    }

    // Prevent the same member from booking the same session more than once.
    if (session.hasMember(memberId)) {

        throw new InvalidBookingException(
                "Member " + memberId +
                " has already booked session " +
                sessionId + "."
        );
    }

    // Check whether the member already has another booking that overlaps with this session.
for (FitnessSession existingSession
        : sessionRepository.findAll()) {

    if (existingSession.hasMember(memberId)) {

        boolean timeOverlap =
                existingSession.overlapsWith(
                        session.getStartTime(),
                        session.getEndTime()
                );

        if (timeOverlap) {

            throw new InvalidBookingException(
                    "Member " + memberId +
                    " already has another booking " +
                    "during this time."
            );
        }
    }
}

    session.addMember(memberId);
}

// BUSINESS OPERATION:
// Returns all sessions booked by a particular member.
public List<FitnessSession> getSessionsForMember(
        String memberId) {

    List<FitnessSession> memberSessions =
            new java.util.ArrayList<>();

    for (FitnessSession session
            : sessionRepository.findAll()) {

        if (session.hasMember(memberId)) {

            memberSessions.add(session);
        }
    }

    return memberSessions;
}
}