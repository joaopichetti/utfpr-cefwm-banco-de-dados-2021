import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/rh";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    private static Connection instance = null;

    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        if (instance == null) {
            instance = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return instance;
    }
}
