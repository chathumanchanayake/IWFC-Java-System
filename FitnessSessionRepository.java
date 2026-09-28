import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Reuses the generic Repository contract for FitnessSession objects.
public class FitnessSessionRepository
        implements Repository<FitnessSession> {

   
    private final Map<String, FitnessSession> sessionMap =
            new HashMap<>();

    @Override
    public void add(FitnessSession session)
            throws DuplicateDataException {

        if (sessionMap.containsKey(session.getSessionId())) {

            throw new DuplicateDataException(
                    "Session with ID " +
                    session.getSessionId() +
                    " already exists."
            );
        }

        sessionMap.put(session.getSessionId(), session);
    }

    @Override
    public FitnessSession findById(String id) {
        return sessionMap.get(id);
    }

    @Override
    public List<FitnessSession> findAll() {
        return new ArrayList<>(sessionMap.values());
    }
}