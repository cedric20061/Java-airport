package console;

import java.util.List;
import java.util.Scanner;

import main.AirportHandler;
import models.Flight;
import models.Reservation;
import models.User;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            int choice = Integer.parseInt(sc.nextLine());

            User user = null;
            if (choice == 1) user = handleLogin(sc);
            else if (choice == 2) user = handleRegister(sc);

            if (user == null) {
                System.out.println("Authentication failed.");
                return;
            }

            if (user.getUserRole() == 1) adminMenu(user, sc);
            else userMenu(user, sc);
        }
    }

    private static User handleLogin(Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        try {
            return AirportHandler.login(username, password);
        } catch (Exception e) {
            System.out.println("Login failed.");
            return null;
        }
    }

    private static User handleRegister(Scanner sc) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        try {
            return AirportHandler.register(username, password, 1);
        } catch (Exception e) {
            System.out.println("Registration failed.");
            return null;
        }
    }

    private static void adminMenu(User user, Scanner sc) {
        System.out.println("Welcome Admin");
        System.out.println("1. Manage Flights");
        System.out.println("2. Manage Reservations");
        System.out.println("3. View Flights");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> flightManagement(sc);
            case 2 -> handleReservation(sc, user);
            case 3 -> AirportHandler.displayFlights();
        }
    }

    private static void flightManagement(Scanner sc) {
        System.out.println("1. Add Flight");
        System.out.println("2. Update Flight");
        System.out.println("3. Delete Flight");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Flight number: ");
                String num = sc.nextLine();
                System.out.print("Destination: ");
                String dest = sc.nextLine();
                System.out.print("Departure date (YYYY-MM-DD): ");
                String date = sc.nextLine();
                System.out.print("Number of seats: ");
                int seats = Integer.parseInt(sc.nextLine());

                Flight flight = new Flight(num, dest, date, seats);
                AirportHandler.addFlight(flight);
                System.out.println("Flight added!");
            }
            case 2 -> {
                System.out.print("Flight number: ");
                String num = sc.nextLine();
                Flight flightToUpdate = AirportHandler.getFlights().stream()
                        .filter(f -> f.getFlightId().equals(num))
                        .findFirst()
                        .orElse(null);
                if (flightToUpdate != null) {
                    System.out.print("New number of seats: ");
                    int newSeats = Integer.parseInt(sc.nextLine());
                    flightToUpdate.setSeatsNumber(newSeats);
                    AirportHandler.saveFlights(AirportHandler.getFlights());
                    System.out.println("Flight updated!");
                } else {
                    System.out.println("Flight not found.");
                }
            }
            case 3 -> {
                System.out.print("Flight number to delete: ");
                String num = sc.nextLine();
                String result = AirportHandler.removeFlight(num);
                System.out.println(result);
            }
        }
    }

    private static void handleReservation(Scanner sc, User user) {
        System.out.print("Client name: ");
        String clientName = sc.nextLine();
        System.out.print("Flight number: ");
        String flightId = sc.nextLine();
        System.out.print("Number of seats: ");
        int seats = Integer.parseInt(sc.nextLine());
        Reservation reservation = new Reservation(user.getUserId(), clientName, flightId, seats);
        AirportHandler.bookFlight(reservation);
        System.out.println("Reservation confirmed.");
    }

    private static void userMenu(User user, Scanner sc) {
        System.out.println("1. View Flights");
        System.out.println("2. My Reservations");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> {
                AirportHandler.displayFlights();
                System.out.println("Do you want to book a flight? 1 = yes, 2 = no");
                if (Integer.parseInt(sc.nextLine()) == 1) {
                    handleReservation(sc, user);
                }
            }
            case 2 -> {
                List<Reservation> res = AirportHandler.getUserReservation(user.getUserId());
                AirportHandler.displayReservations(res);
                // TODO: Add update/delete reservation options
            }
        }
    }
}
