import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// JUNIT TEST CLASS
// Tests important scheduling and booking behaviour in FitnessSessionService.
public class FitnessSessionServiceTest {

    @Test
void shouldScheduleValidSession()
        throws DuplicateDataException,
        InvalidBookingException {

    // Arrange
    FitnessSessionRepository repository =
            new FitnessSessionRepository();

    FitnessSessionService service =
            new FitnessSessionService(repository);

    // Act
    service.scheduleSession(
            "S001",
            "Yoga",
            "Studio A",
            LocalDateTime.of(2026, 10, 1, 10, 0),
            LocalDateTime.of(2026, 10, 1, 11, 0),
            "I001",
            "E001"
    );

    // Assert
    assertEquals(
            1,
            service.getAllSessions().size()
    );
}

@Test
void shouldRejectStudioDoubleBooking()
        throws DuplicateDataException,
        InvalidBookingException {

    // Arrange
    FitnessSessionRepository repository =
            new FitnessSessionRepository();

    FitnessSessionService service =
            new FitnessSessionService(repository);

    // Schedule the first valid session.
    service.scheduleSession(
            "S001",
            "Yoga",
            "Studio A",
            LocalDateTime.of(2026, 10, 1, 10, 0),
            LocalDateTime.of(2026, 10, 1, 11, 0),
            "I001",
            "E001"
    );

    // Act + Assert
    // A second session overlaps in the same studio,
    // so InvalidBookingException must be thrown.
    assertThrows(
            InvalidBookingException.class,
            () -> service.scheduleSession(
                    "S002",
                    "Pilates",
                    "Studio A",
                    LocalDateTime.of(2026, 10, 1, 10, 30),
                    LocalDateTime.of(2026, 10, 1, 11, 30),
                    "I002",
                    "E002"
            )
    );
}

@Test
void shouldRejectSessionOutsideOperatingHours() {

    // Arrange
    FitnessSessionRepository repository =
            new FitnessSessionRepository();

    FitnessSessionService service =
            new FitnessSessionService(repository);

    // Act + Assert
    // The center opens at 06:00,
    // so a session starting at 05:00 must be rejected.
    assertThrows(
            InvalidBookingException.class,
            () -> service.scheduleSession(
                    "S003",
                    "Early Morning Yoga",
                    "Studio C",
                    LocalDateTime.of(2026, 10, 1, 5, 0),
                    LocalDateTime.of(2026, 10, 1, 6, 0),
                    "I001",
                    "E003"
            )
    );
}
@Test
void shouldRejectMemberTimeConflict()
        throws DuplicateDataException,
        InvalidBookingException {

    // Arrange
    FitnessSessionRepository repository =
            new FitnessSessionRepository();

    FitnessSessionService service =
            new FitnessSessionService(repository);

    // Two sessions at overlapping times,
    // but different studios and equipment.
    service.scheduleSession(
            "S001",
            "Yoga",
            "Studio A",
            LocalDateTime.of(2026, 10, 1, 10, 0),
            LocalDateTime.of(2026, 10, 1, 11, 0),
            "I001",
            "E001"
    );

    service.scheduleSession(
            "S002",
            "Pilates",
            "Studio B",
            LocalDateTime.of(2026, 10, 1, 10, 30),
            LocalDateTime.of(2026, 10, 1, 11, 30),
            "I002",
            "E002"
    );

    // Member successfully books the first session.
    service.bookSession(
            "S001",
            "M001"
    );

    // Act + Assert
    // The same member must not be able to book
    // another session that overlaps in time.
    assertThrows(
            InvalidBookingException.class,
            () -> service.bookSession(
                    "S002",
                    "M001"
            )
    );
}
}