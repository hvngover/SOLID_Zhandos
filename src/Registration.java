import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Registration {
    private DBMethods dbMethods = new DBMethods();
    private Statement statement = null;
    private ResultSet rs = null;

    public void register() throws SQLException {
        Scanner scan = new Scanner(System.in);
        dbMethods.connect_to_db("postgres", "postgres", "Aisultan2015");
        statement = dbMethods.createStatement();

        System.out.println("Enter username: ");
        String username = scan.nextLine();
        System.out.println("Enter iin: ");
        String iin = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();

        String hashedPassword = hashPassword(password);

        String query = "INSERT INTO users_table(username, iin, password) VALUES('" + username + "', '" + iin + "', '" + hashedPassword + "')";
        statement.executeUpdate(query);

        System.out.println("Successfully registered.");
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
