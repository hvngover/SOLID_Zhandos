import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Random;

public class Login extends Captcha {
    private DBMethods dbMethods = new DBMethods();
    private Statement statement = null;
    private ResultSet rs = null;
    private int loginAttempts = 3;

    public void login() throws SQLException {
        Scanner scan = new Scanner(System.in);
        dbMethods.connect_to_db("postgres", "postgres", "Aisultan2015");
        statement = dbMethods.createStatement();

        System.out.println("Enter username or iin: ");
        String usernameOrIin = scan.nextLine();
        System.out.println("Enter password: ");
        String password = scan.nextLine();


        String query = "";
        if (isNumeric(usernameOrIin)) {
            query = "SELECT password FROM users_table WHERE iin='" + usernameOrIin + "'";
        } else {
            query = "SELECT password FROM users_table WHERE username='" + usernameOrIin + "'";
        }
        rs = statement.executeQuery(query);
        if (rs.next()) {
            String hashedPasswordFromDB = rs.getString("password");
            if (hashedPasswordFromDB.equals(hashPassword(password))) {
                System.out.println("Successfully logged in.");
                loginAttempts = 3;
            } else {
                System.out.println("Incorrect username or password.");
                loginAttempts--;
                System.out.println("You have " + loginAttempts + " attempts left. Try again.");
                if (loginAttempts == 0) {
                    String captcha = generateCaptcha();
                    System.out.println("Too many login attempts. Input the following captcha to continue: " + captcha);
                    String userInput = scan.nextLine();
                    if (userInput.equals(captcha)) {
                        System.out.println("Correct captcha. Please login again.");
                        loginAttempts = 3;
                        login();
                    } else {
                        System.out.println("Incorrect captcha. Exiting the program");
                        System.exit(0);
                    }
                } else {
                    login();
                }
            }
        } else {
            System.out.println("Username or iin not found in the database. Try again.");
            login();
        }
    }


    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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
