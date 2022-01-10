package easv.app.dal.db;

public interface IDatabaseCRUD<T>
{
    void create(T input);
    T read(T input);
    T[] readAll();
    void update(T input);
    void delete(T input);
}
