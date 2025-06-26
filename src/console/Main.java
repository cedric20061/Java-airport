package console;

import java.util.List;
import java.util.Scanner;

import controllers.FlightController;
import controllers.ReservationController;
import controllers.UserController;
import models.Flight;
import models.Reservation;
import models.User;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            User user = null;

            while (user == null) {
                System.out.println("== Welcome to Airport Manager ==");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.print("Choice: ");
                String input = sc.nextLine();

                switch (input) {
                    case "1" -> user = handleLogin(sc);
                    case "2" -> user = handleRegister(sc);
                    default -> System.out.println("Invalid option. Try again.");
                }
            }

            if (user.getUserRole() == 1) adminMenu(sc, user);
            else userMenu(sc, user);
        }
    }

    private static User handleLogin(Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        User user = UserController.login(username, password);
        if (user == null) {
            System.out.println("Login failed. Try again.");
        }
        return user;
    }

    private static User handleRegister(Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        return UserController.register(username, password, 2); // Default to role 2 (client)
    }

    private static void adminMenu(Scanner sc, User user) {
        while (true) {
            System.out.println("\n== Admin Menu ==");
            System.out.println("1. Manage Flights");
            System.out.println("2. Manage Reservations");
            System.out.println("3. View All Flights");
            System.out.println("4. Logout");

            System.out.print("Choice: ");
            String input = sc.nextLine();

            switch (input) {
                case "1" -> manageFlights(sc);
                case "2" -> manageReservations(sc, ReservationController.getReservations(), user);
                case "3" -> FlightController.displayFlights();
                case "4" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void manageFlights(Scanner sc) {
        System.out.println("\n== Flight Management ==");
        System.out.println("1. Add Flight");
        System.out.println("2. Update Flight");
        System.out.println("3. Delete Flight");
        System.out.print("Choice: ");
        String input = sc.nextLine();

        switch (input) {
            case "1" -> {
                System.out.print("Flight number: ");
                String num = sc.nextLine();
                System.out.print("Destination: ");
                String dest = sc.nextLine();
                System.out.print("Departure date (YYYY-MM-DD): ");
                String date = sc.nextLine();
                System.out.print("Number of seats: ");
                int seats = Integer.parseInt(sc.nextLine());

                Flight flight = new Flight(num, dest, date, seats);
                FlightController.addFlight(flight);
                System.out.println("Flight added.");
            }
            case "2" -> {
                System.out.print("Flight number: ");
                String num = sc.nextLine();
                Flight flight = FlightController.getFlightById(num);
                if (flight != null) {
                    System.out.print("New number of seats: ");
                    int seats = Integer.parseInt(sc.nextLine());
                    flight.setSeatsNumber(seats);
                    FlightController.saveFlights(FlightController.getFlights());
                    System.out.println("Flight updated.");
                } else {
                    System.out.println("Flight not found.");
                }
            }
            case "3" -> {
                System.out.print("Flight number: ");
                String num = sc.nextLine();
                FlightController.deleteFlight(num);
                System.out.println("Flight deleted if existed.");
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void manageReservations(Scanner sc, List<Reservation> reservations, User user) {
        ReservationController.displayReservations(reservations);
        System.out.println("\n== Reservation Management ==");
        System.out.println("1. Add Reservation");
        System.out.println("2. Update Reservation");
        System.out.println("3. Cancel Reservation");
        System.out.print("Choice: ");
        String input = sc.nextLine();

        switch (input) {
            case "1" -> {
                System.out.print("Flight ID: ");
                String flightId = sc.nextLine();
                System.out.print("Seats to reserve: ");
                int seats = Integer.parseInt(sc.nextLine());
                int result = ReservationController.bookFlight(user.getUserId(), user.getUsername(), flightId, seats);
                if (result > 0) System.out.println("Reservation created. ID: " + result);
                else System.out.println("Reservation failed. Not enough seats or flight not found.");
            }
            case "2" -> {
                System.out.print("Reservation ID: ");
                int resId = Integer.parseInt(sc.nextLine());
                System.out.print("New seat count: ");
                int newSeats = Integer.parseInt(sc.nextLine());
                boolean ok = ReservationController.updateReservation(resId, newSeats);
                System.out.println(ok ? "Reservation updated." : "Update failed.");
            }
            case "3" -> {
                System.out.print("Reservation ID: ");
                int resId = Integer.parseInt(sc.nextLine());
                boolean canceled = ReservationController.cancelReservation(resId);
                System.out.println(canceled ? "Reservation cancelled." : "Cancellation failed.");
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void userMenu(Scanner sc, User user) {
        while (true) {
            System.out.println("\n== User Menu ==");
            System.out.println("1. View Flights");
            System.out.println("2. My Reservations");
            System.out.println("3. Logout");

            System.out.print("Choice: ");
            String input = sc.nextLine();

            switch (input) {
                case "1" -> FlightController.displayFlights();
                case "2" -> {
                    List<Reservation> userRes = ReservationController.getUserReservations(user.getUserId());
                    manageReservations(sc, userRes, user);
                }
                case "3" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
