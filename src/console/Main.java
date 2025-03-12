package src.console;

import java.util.List;
import java.util.Scanner;
import src.models.User;
import src.models.Passenger;
import src.models.Admin;
import src.models.Flight;
import src.services.FlightService;
import src.services.UserService;
import src.utils.FlightUtils;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static User user;

    public static void main(String[] args) {
        while (true) {
            user = loginInterface();

            if (user != null) {
                if (user instanceof Passenger) {
                    passengerScreen((Passenger) user);
                } else if (user instanceof Admin) {
                    adminScreen((Admin) user);
                }
            } else {
                System.out.println("Authentication failed. Try again.");
            }
        }
    }

    public static User loginInterface() {
        System.out.println("\nWelcome to The Airport\nPlease Login or Register\n1) Login\n2) Register");
        int action = getValidIntegerInput();

        System.out.print("Your email: ");
        String mail = scanner.next();
        
        System.out.print("Your password: ");
        String password = scanner.next();

        switch (action) {
            case 1:
                return UserService.login(mail, password);
            case 2:
                System.out.print("Your name: ");
                String name = scanner.next();
                return UserService.register(name, mail, password);
            default:
                System.out.println("Invalid choice.");
                return null;
        }
    }

    public static void passengerScreen(Passenger passenger) {
        while (true) {
            System.out.println("\nWelcome to the passenger home screen");
            System.out.println("1) Book a flight");
            System.out.println("2) See your reservations");
            System.out.println("3) Logout");
            System.out.print("Choose an option: ");

            int choice = getValidIntegerInput();
            switch (choice) {
                case 1:
                    List<Flight> flights = FlightService.getFlights();
                    FlightUtils.displayFlights(flights);
                    System.out.print("Enter flight ID to book: ");
                    int flightId = getValidIntegerInput();
                    boolean success = passenger.bookFlight(flightId);
                    System.out.println(success ? "Flight booked successfully!" : "Booking failed.");
                    break;
                case 2:
                    List<Flight> reservations = passenger.getBookedFlights();
                    if (reservations.isEmpty()) {
                        System.out.println("You have no reservations.");
                    } else {
                        System.out.println("Your reservations:");
                        FlightUtils.displayFlights(reservations);
                    }
                    break;
                case 3:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void adminScreen(Admin admin) {
        while (true) {
            System.out.println("\nWelcome to the admin panel");
            System.out.println("1) Add a flight");
            System.out.println("2) Update a flight");
            System.out.println("3) Remove a flight");
            System.out.println("4) Logout");
            System.out.print("Choose an option: ");

            int choice = getValidIntegerInput();
            switch (choice) {
                case 1:
                    System.out.print("Enter flight code: ");
                    String flightCode = scanner.next();
                    System.out.print("Enter destination: ");
                    String destination = scanner.next();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.next();
                    System.out.print("Enter available seats: ");
                    int seats = getValidIntegerInput();
                    boolean added = admin.addFlight(flightCode, destination, date, seats);
                    System.out.println(added ? "Flight added successfully!" : "Failed to add flight.");
                    break;
                case 2:
                    System.out.print("Enter flight'id to update");
                    int flightId = getValidIntegerInput();
                    System.out.print("Enter flight code: ");
                    flightCode = scanner.next();
                    System.out.print("Enter destination: ");
                    destination = scanner.next();
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    date = scanner.next();
                    System.out.print("Enter available seats: ");
                    seats = getValidIntegerInput();
                    boolean updated = admin.updateFlight(flightId, flightCode, destination, date, seats);
                    System.out.println(updated ? "Flight updated successfully!" : "Failed to update flight.");
                    break;
                case 3:
                    System.out.print("Enter flight ID to remove: ");
                    flightId = getValidIntegerInput();
                    boolean removed = admin.removeFlight(flightId);
                    System.out.println(removed ? "Flight removed successfully!" : "Failed to remove flight.");
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void logout() {
        System.out.println("Logging out...");
        user = null;
    }

    private static int getValidIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
