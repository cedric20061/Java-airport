package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Flight;
import models.Reservation;

public class AirportHandler {
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
        return "Flight added successfully.";
    }

    public static String removeFlight(String flightNumber) {
        List<Flight> flights = getFlights();
        boolean removed = flights.removeIf(flight -> flight.getFlightId().equals(flightNumber));
        if (removed) {
            saveFlights(flights);
            return "Flight removed successfully.";
        } else {
            return "Flight not found.";
        }
    }
    
    public static String bookFlight(Reservation reservation) {
        List<Flight> flights = getFlights();
        List<Reservation> reservations = getReservations();
        for (Flight flight : flights) {
            if (flight.getFlightId().equals(reservation.getFlightId())) {
                if (flight.getSeatsNumber() >= reservation.getSeatsReserved()) {
                    flight.setSeatsNumber(flight.getSeatsNumber() - reservation.getSeatsReserved());
                    saveFlights(flights);
                    reservations.add(reservation);
                    saveReservations(reservations);
                    return "Booking successful.";
                } else {
                    return "Not enough seats available.";
                }
            }
        }
        return "Flight not found.";
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
}
