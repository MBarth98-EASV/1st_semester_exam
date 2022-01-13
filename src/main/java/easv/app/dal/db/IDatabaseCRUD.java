package easv.app.dal.db;

import java.sql.SQLException;

public interface IDatabaseCRUD<T>
{
    T create(T input) throws SQLException;
    T read(T input);
    T[] readAll();
    void update(T input);
    void delete(T input) throws SQLException;
}
