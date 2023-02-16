import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBMethods {
    private Connection conn = null;
    private Statement statement = null;

    public Connection connect_to_db(String dbname, String username, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, username, password);
            if (conn == null) {
                System.out.println("Connection Failed!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return conn;
    }

    public Statement createStatement() throws SQLException {
        statement = conn.createStatement();
        return statement;
    }
}
