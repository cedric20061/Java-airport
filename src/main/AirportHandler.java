package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Flight;
import models.Reservation;
import models.User;

public class AirportHandler {
    public static User register(String username, String password, int role) {
        int newId = 1;

        File file = new File("./data/users.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                String lastLine = null;

                while ((line = br.readLine()) != null) {
                    lastLine = line;
                }

                if (lastLine != null) {
                    String[] parts = lastLine.split(",");
                    if (parts.length > 0) {
                        newId = Integer.parseInt(parts[0]) + 1;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(newId + "," + username + "," + password + "," + role);
            bw.newLine();
            return new User(newId, username, password, role);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[1].equals(username) && parts[2].equals(password)) {
                    return User.fromString(line); // Return role
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Error code
        }
        return null; // User not found
    }
    public static List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/flights.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                flights.add(Flight.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flights;
    };
    public static List<Reservation> getReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/reservations.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                reservations.add(Reservation.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    public static List<Reservation> getUserReservation(int id){
        List<Reservation> userReservations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./data/reservations.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");
                if(Integer.parseInt(parts[0]) == id){
                    userReservations.add(Reservation.fromString(line));
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return userReservations;
    }
    public static void saveReservations(List<Reservation> reservations) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/reservations.txt"))) {
            for (Reservation reservation : reservations) {
                bw.write(reservation.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveFlights(List<Flight> flights) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./data/flights.txt"))) {
            for (Flight flight : flights) {
                bw.write(flight.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String addFlight(Flight flight) {
        List<Flight> flights = getFlights();
        flights.add(flight);
        saveFlights(flights);
        return flight.getFlightId();
    }

    public static String removeFlight(String flightNumber) {
        List<Flight> flights = getFlights();
        boolean removed = flights.removeIf(flight -> flight.getFlightId().equals(flightNumber));
        if (removed) {
            saveFlights(flights);
            return flightNumber;
        } else {
            return null;
        }
    }
    
    public static int bookFlight(int clientId, String clientName, String flightId, int seats) {
        List<Flight> flights = getFlights();
        List<Reservation> reservations = getReservations();
        for (Flight flight : flights) {
            if (flight.getFlightId().equals(flightId)) {
                if (flight.getSeatsNumber() >= seats) {
                    flight.setSeatsNumber(flight.getSeatsNumber() - seats);
                    saveFlights(flights);
                    int newId = 1;

                    File file = new File("./data/reservations.txt");
                    if (file.exists()) {
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            String line;
                            String lastLine = null;

                            while ((line = br.readLine()) != null) {
                                lastLine = line;
                            }

                            if (lastLine != null) {
                                String[] parts = lastLine.split(",");
                                if (parts.length > 0) {
                                    newId = Integer.parseInt(parts[0]) + 1;
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Reservation reservation = new Reservation(newId, clientId, clientName, flightId, seats);
                    reservations.add(reservation);
                    saveReservations(reservations);
                    return newId;
                } else {
                    return -1;
                }
            }
        }
        return -2;
    }
    public static Flight getFlightById(String id){
        return getFlights()
                .stream()
                .filter(f->f.getFlightId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public static String cancelReservation(String clientName, String flightId) {
        List<Flight> flights = getFlights();
        List<Reservation> reservations = getReservations();
        for (Reservation reservation : reservations) {
            if (reservation.getClientName().equals(clientName) && reservation.getFlightId().equals(flightId)) {
                reservations.remove(reservation);
                saveReservations(reservations);
                for (Flight flight : flights) {
                    if (flight.getFlightId().equals(flightId)) {
                        flight.setSeatsNumber(flight.getSeatsNumber() + reservation.getSeatsReserved());
                        saveFlights(flights);
                        return "Reservation cancelled successfully.";
                    }
                }
            }
        }
        return "Reservation not found.";
    }

    public static void displayFlights() {
        List<Flight> flights = getFlights();
        if (flights.isEmpty()) {
            System.out.println("Aucun vol disponible.");
            return;
        }
        System.out.println("Liste des vols :");
        System.out.println("╔════════════╦══════════════════╦═══════════════╦════════════════════╗");
        System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18s ║%n", "Vol ID", "Destination", "Départ", "Places dispo");
        System.out.println("╠════════════╬══════════════════╬═══════════════╬════════════════════╣");

        flights.forEach(flightItem -> {
            System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18d ║%n",
                flightItem.getFlightId(),
                flightItem.getDestination(),
                flightItem.getDateDepart(),
                flightItem.getSeatsNumber());
        });
        System.out.println("╚════════════╩══════════════════╩═══════════════╩════════════════════╝");
    }

    public static void displayReservations(List<Reservation> reservations){
        if (reservations.isEmpty()) {
            System.out.println("No flights booked.");
            return;
        }
        System.out.println("════════════════════Reservations list════════════════════");
        System.out.println("╔════════════╦══════════════════╦═══════════════╦════════════════════╗");
        System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18s ║%n", "Vol ID", "Destination", "Départ", "Seats number");
        System.out.println("╠════════════╬══════════════════╬═══════════════╬════════════════════╣");

        reservations.forEach(reservation->{
            Flight flight = AirportHandler.getFlightById(reservation.getFlightId());
            System.out.printf("║ %-10s ║ %-16s ║ %-13s ║ %-18d ║%n",
                flight.getFlightId(),
                flight.getDestination(),
                flight.getDateDepart(),
                flight.getSeatsNumber());
        });
        System.out.println("╚════════════╩══════════════════╩═══════════════╩════════════════════╝");

    }
}
