import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter 'R' for registration or 'L' for login: ");
        String input = scan.nextLine();
        if (input.equals("R")) {
            Registration registration = new Registration();
            registration.register();
        } else if (input.equals("L")) {
            Login login = new Login();
            login.login();
        } else {
            System.out.println("Invalid input. Try again.");
        }
    }
}