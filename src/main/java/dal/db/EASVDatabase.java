package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EASVDatabase {

    private SQLServerDataSource dbaccess;

    public EASVDatabase()
    {
        dbaccess = new SQLServerDataSource();
        dbaccess.setServerName("10.176.111.31");
        dbaccess.setDatabaseName("CSe21A_29_MovieManager");
        dbaccess.setUser("CSe21A_29");
        dbaccess.setPassword("itsikkerhed");
        dbaccess.setPortNumber(1433);
    }

    public Connection getConnection()
    {
        try {
            return dbaccess.getConnection();
        }
        catch (SQLException e)
        {
            return null;
        }
    }

    private void execute(String sql)
    {
        try
        {
            Statement statement = dbaccess.getConnection().createStatement();
            statement.execute(sql);
        }
        catch(SQLException e)
        {

        }

    }

    private void addMovie(String name, int rating, String filepath, String imdblink, int lastview)
    {

    }



}
