import java.util.Scanner;

public class HotelReservationUI {
    private ReservationController reservationController;
    private ClientController clientController;
    //private ManagerController managerController;
    private AuthenticationManager authenticationManager;
    private Scanner scanner;

    public HotelReservationUI() {
        this.reservationController = new ReservationController(new DatabaseManager(), new EmailService());
        this.clientController = new ClientController(new DatabaseManager());
        //this.managerController = new ManagerController(new DatabaseManager());
        this.authenticationManager = new AuthenticationManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        displayMenu();
        int choice = Integer.parseInt(scanner.nextLine());
        handleMenuSelection(choice);
    }

    private void displayMenu() {
        System.out.println("Welcome to the Hotel Reservation System!");
        System.out.println("1. Register as a new client");
        System.out.println("2. Log in as a client");
        System.out.println("3. Log in as a manager");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleMenuSelection(int choice) {
        switch (choice) {
            case 1:
                registerClient();
                break;
            case 2:
                logInAsClient();
                break;
            case 3:
                logInAsManager();
                break;
            case 4:
                System.out.println("Exiting the system...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
                start();
        }
    }

    private void registerClient() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        clientController.registerClient(name, email);
        System.out.println("Registration successful! You are now logged in as " + name);
        // Implement any further actions after registration
    }

    private void logInAsClient() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        AuthenticationManager authManager = new AuthenticationManager();
        if (authManager.authenticateClient(email, password)) {
            Client client = new Client("Dummy Client", email);
            System.out.println("Login successful! Welcome, " + client.getName());
            // Implement any further actions after client login
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }
    
    private void logInAsManager() {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        AuthenticationManager authManager = new AuthenticationManager();
        if (authManager.authenticateManager(email, password)) {
            Manager manager = new Manager("Dummy Manager", email);
            System.out.println("Login successful! Welcome, " + manager.getName());
            // Implement any further actions after manager login
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

    public static void main(String[] args) {
        HotelReservationUI hotelUI = new HotelReservationUI();
        hotelUI.start();
    }
}

