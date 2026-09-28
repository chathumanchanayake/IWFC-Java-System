import java.util.List;

// ADVANCED OOP: Generic interface
public interface Repository<T> {

    void add(T item) throws DuplicateDataException;

    T findById(String id);

    List<T> findAll();
}